package com.spring.utils;

import org.springframework.oxm.Marshaller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-11-17
 * Time: 上午9:09
 * Description:
 */
public class FullMarshallingView extends MarshallingView {

    private Marshaller marshaller;

    public FullMarshallingView() {
        setContentType(DEFAULT_CONTENT_TYPE);
    }

    public FullMarshallingView(Marshaller marshaller) {
        Assert.notNull(marshaller, "'marshaller' must not be null");
        setContentType(DEFAULT_CONTENT_TYPE);
        this.marshaller = marshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        Assert.notNull(marshaller, "'marshaller' must not be null");
        this.marshaller = marshaller;
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Object> toBeMarshalled = locateToBeMarshalled(model);
        if (toBeMarshalled.size() == 0) {
            throw new ServletException("Unable to locate objects to be marshalled in model: " + model);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);

        for (Object o : toBeMarshalled) {
            ByteArrayOutputStream marshallerResult = new ByteArrayOutputStream();
            marshaller.marshal(o, new StreamResult(marshallerResult));
            FileCopyUtils.copy(marshallerResult.toByteArray(), bos);
        }

        response.setContentType(getContentType());
        response.setContentLength(bos.size());

        FileCopyUtils.copy(bos.toByteArray(), response.getOutputStream());
    }

    @Override
    protected List<Object> locateToBeMarshalled(Map model) throws ServletException {
        List<Object> toBeMarshalled = new ArrayList<Object>();
        for (Object o : model.values()) {
            if (this.marshaller.supports(o.getClass())) {
                toBeMarshalled.add(o);
            }
        }
        return toBeMarshalled;
    }

}
