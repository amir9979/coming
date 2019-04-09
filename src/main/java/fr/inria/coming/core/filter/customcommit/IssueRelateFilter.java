package fr.inria.coming.core.filter.customcommit;
import com.alibaba.fastjson.JSONArray;
import fr.inria.coming.core.entities.interfaces.Commit;
import fr.inria.coming.core.filter.AbstractChainedFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;


public class IssueRelateFilter extends AbstractChainedFilter<Commit> {
    private HashSet<String> targetShaSet;

    public IssueRelateFilter() throws IOException {
        super();
        String content = new String ( Files.readAllBytes( Paths.get("adConfig.json")));
        targetShaSet = Json2set(content);
    }

    private static HashSet<String> Json2set(String jsonObj) {

        JSONArray jsonArray = JSONArray.parseArray(jsonObj);
        return new HashSet<String>(jsonArray.toJavaList(String.class));
    }

    @Override
    public boolean accept(Commit c) {
        if (super.accept(c)) {
            String commitId = c.getName();
            return targetShaSet.contains(commitId);
        }
        return false;
    }
}
