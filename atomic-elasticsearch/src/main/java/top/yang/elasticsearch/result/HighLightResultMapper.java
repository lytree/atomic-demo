package top.yang.elasticsearch.result;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;

public class HighLightResultMapper {

    public void buildHighLight() {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
    }

}
