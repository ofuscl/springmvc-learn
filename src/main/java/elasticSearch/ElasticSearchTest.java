package elasticSearch;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * Created by YScredit on 2018/12/13.
 */
public class ElasticSearchTest {

    public static void main(String[] args) {

    }

    // 使用QueryBuilder
    public void queryBuilder(List<String> entNameList){
        QueryBuilder qb = nestedQuery("path",boolQuery().must(termsQuery("litigants.name.keyword", entNameList)), ScoreMode.Avg);
        TransportClient client = null;
        SearchRequestBuilder request = client.prepareSearch().setTypes().setQuery(qb).setSize(0)
                //只按照案件类型分组  // missing ---默认-其他
                .addAggregation(AggregationBuilders.terms("group_by_type").field("type").missing("其他").size(100))
                //只按照文书类型分组  // missing ---默认-其他文书
                .addAggregation(AggregationBuilders.terms("group_by_content_type").field("content_type").missing("其他文书").size(100))
                .setTimeout(new TimeValue(1000));
    }

    // 使用QueryBuilder
    public void queryS(){
        //（1）统计某个字段的数量
        ValueCountAggregationBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
        //（2）去重统计某个字段的数量（有少量误差）
        CardinalityAggregationBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
        //（3）聚合过滤
        FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter",QueryBuilders.queryStringQuery("uid:001"));
        //（4）按某个字段分组
        TermsAggregationBuilder tb=  AggregationBuilders.terms("group_name").field("name");
        //（5）求和
        SumAggregationBuilder sumBuilder=	AggregationBuilders.sum("sum_price").field("price");
        //（6）求平均
        AvgAggregationBuilder ab= AggregationBuilders.avg("avg_price").field("price");
        //（7）求最大值
        MaxAggregationBuilder mb= AggregationBuilders.max("max_price").field("price");
        //（8）求最小值
        MinAggregationBuilder min=	AggregationBuilders.min("min_price").field("price");
        //（9）按日期间隔分组
        DateHistogramAggregationBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
        //（10）获取聚合里面的结果
        TopHitsAggregationBuilder thb=  AggregationBuilders.topHits("top_result");
        //（11）嵌套的聚合
        NestedAggregationBuilder nb= AggregationBuilders.nested("negsted_path","quests");
        //（12）反转嵌套
        AggregationBuilders.reverseNested("res_negsted").path("kps ");

    }
}
