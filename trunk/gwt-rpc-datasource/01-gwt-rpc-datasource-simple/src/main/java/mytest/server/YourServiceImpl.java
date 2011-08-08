package mytest.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import mytest.client.YourService;
import mytest.shared.YourDataObject;
import mytest.shared.lib.GenericGwtRpcList;

import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class YourServiceImpl extends RemoteServiceServlet implements
        YourService {

    private static List<YourDataObject> items;

    static {
        items = new ArrayList<YourDataObject>();
        YourDataObject item;

        for (int i = 0; i < 200; i++) {
            item = new YourDataObject();
            item.setId(i);
            item.setName("Item " + i);
            item.setLocation("Loc: " + Math.random());
            items.add(item);
        }
    }

    public YourDataObject add(YourDataObject data) {
        // the new data object passed by the client does not have an id yet,
        // so we add the next highest.
        data.setId(items.size());
        items.add(data);
        return data;
    }


    public void remove(YourDataObject data) {
        items.remove(getById(data.getId()));
    }

    public YourDataObject update(YourDataObject data) {
        items.remove(getById(data.getId()));
        items.add(data);
        return data;
    }

    private YourDataObject getById(int id) {
        for (YourDataObject item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<YourDataObject> fetch(Integer startRow, Integer endRow, final String sortBy, Map<String, String> filterCriteria) {

        GenericGwtRpcList<YourDataObject> outList = new GenericGwtRpcList<YourDataObject>();

        // we copy the original list, cause we do not want to alter it.
        List<YourDataObject> out = new ArrayList<YourDataObject>(items);

        // to implement filtering, retrieve the filter criterias from filterCriteria and build your query accordingly.
        // for the sake of argument, we filter the list:
        if (filterCriteria != null && filterCriteria.size() > 0) {

            List<YourDataObject> filteredList = new ArrayList<YourDataObject>();

            if (filterCriteria.containsKey("name")) {
                String pattern = ".*" + filterCriteria.get("name") + ".*";
                for (YourDataObject yourDataObject : out) {
                    if (Pattern.matches(pattern, yourDataObject.getName())) {
                        filteredList.add(yourDataObject);
                    }
                }
            }

            if (filterCriteria.containsKey("location")) {
                String pattern = ".*" + filterCriteria.get("location") + ".*";
                for (YourDataObject yourDataObject : out) {
                    if (Pattern.matches(pattern, yourDataObject.getLocation())) {
                        filteredList.add(yourDataObject);
                    }
                }
            }

            out = filteredList;
        }

        // to implement sorting, use the transfered sortBy String. It contains the field name to sort, 
        // preceded by a '-' if the sorting is to be descending.
        // of course, the following example is slow as hell, but if you use a database, you should be quite fast...
        Comparator<YourDataObject> c = new Comparator<YourDataObject>() {

            public int compare(YourDataObject o1, YourDataObject o2) {
                if ("name".equals(sortBy)) {
                    return (o1.getName().compareTo(o2.getName()));
                }
                if ("location".equals(sortBy)) {
                    return (o1.getLocation().compareTo(o2.getLocation()));
                }
                if ("-name".equals(sortBy)) {
                    return (o2.getName().compareTo(o1.getName()));
                }
                if ("-location".equals(sortBy)) {
                    return (o2.getLocation().compareTo(o1.getLocation()));
                }
                return 0;
            }
        };

        Collections.sort(out, c);


        // if startRow and endRow are not null, the client wishes paged fetching.
        if (startRow != null && endRow != null) {
            // extract the requested part
            for (int i = startRow; (i < endRow && i < out.size()); i++) {
                outList.add(out.get(i));
            }
        } else {
            outList.addAll(out);
        }

        // important to allow proper paging, we set the total counts of rows.
        outList.setTotalRows(out.size());

        return outList;

    }

}