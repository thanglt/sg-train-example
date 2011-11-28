package com.spring.utils;

import org.springframework.oxm.Marshaller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 11-11-17
 * Time: 上午9:09
 * Description:
 */
public class FullMarshallingView2 extends AbstractView {

    private Marshaller marshaller;
    public static final String DEFAULT_CONTENT_TYPE = "application/xml";

    public FullMarshallingView2() {
        setContentType(DEFAULT_CONTENT_TYPE);
    }

    public FullMarshallingView2(Marshaller marshaller) {
        Assert.notNull(marshaller, "'marshaller' must not be null");
        setContentType(DEFAULT_CONTENT_TYPE);
        this.marshaller = marshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        Assert.notNull(marshaller, "'marshaller' must not be null");
        this.marshaller = marshaller;
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Object> toBeMarshalled = locateToBeMarshalled(model);
        if (toBeMarshalled.size() == 0) {
            throw new ServletException("Unable to locate objects to be marshalled in model: " + model);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);

        for (int i = 0; i < toBeMarshalled.size(); i++) {
            Object o = toBeMarshalled.get(i);
            if (i == 0) {
                ByteArrayOutputStream marshallerResult = new ByteArrayOutputStream();
                marshaller.marshal(o, new StreamResult(marshallerResult));
                FileCopyUtils.copy(marshallerResult.toByteArray(), bos);
            }else{
                ByteArrayOutputStream marshallerResult = new ByteArrayOutputStream();
                marshaller.marshal(o, new StreamResult(marshallerResult));
                String string = String.valueOf(bos);
                int trimStartPos = string.lastIndexOf("<?xml version=\"");
                int trimEndPos = string.lastIndexOf("?>") + 2;
                string = string.replace(string.substring(trimStartPos,trimEndPos) , "");
                byte[] src = string.getBytes();
                FileCopyUtils.copy(src, bos);
            }
        }

//        if (!hasOnlyOneRoot(bos)) {
//            bos = trimDuplicatedRoot(bos);
//        }

        response.setContentType(getContentType());
        response.setContentLength(bos.size());

        FileCopyUtils.copy(bos.toByteArray(), response.getOutputStream());
    }

    private void validate(ByteArrayOutputStream bos) {
        if (!hasOnlyOneRoot(bos)) {
            trimDuplicatedRoot(bos);
        }
    }

    private ByteArrayOutputStream trimDuplicatedRoot(ByteArrayOutputStream bos) {
        String string = String.valueOf(bos);
        int trimStartPos = string.lastIndexOf("<?xml version=\"");
        int trimEndPos = string.lastIndexOf("?>") + 2;
        String part1 = string.substring(0, trimStartPos);
        String part2 = string.substring(trimEndPos, string.length());
//        String result = part1 + part2;
//        System.out.println(result);
//        byte[] src = result.getBytes();
//        ByteArrayInputStream in = new ByteArrayInputStream(src);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        transform(in, out);
//        return out;


        return null;
    }

    public static void transform(InputStream in, OutputStream out) {
        int ch = 0;
        try {
            while ((ch = in.read()) != -1) {
                int UpperCh = Character.toUpperCase((char) ch);
                out.write(UpperCh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasOnlyOneRoot(ByteArrayOutputStream bos) {
        String string = String.valueOf(bos);
        return string.lastIndexOf("<?xml version=\"") == 0;
    }

    protected List<Object> locateToBeMarshalled(Map model) throws ServletException {
        List<Object> toBeMarshalled = new ArrayList<Object>();
        for (Object o : model.values()) {
            if (Collection.class.isAssignableFrom(o.getClass())) {
                for (Object t : (Collection) o) {
                    if (this.marshaller.supports(t.getClass())) {
                        toBeMarshalled.add(t);
                    }
                }
            } else if (this.marshaller.supports(o.getClass())) {
                toBeMarshalled.add(o);
            }
        }
        return toBeMarshalled;
    }

}
