package com.mycompany.lucene;

import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.mycompany.entity.Blog;
import com.mycompany.util.DateUtil;
import com.mycompany.util.StringUtil;

public class BlogIndex {

	private static final String indexDir = "D:\\Project-Data\\blog-data\\lucene";

	private Directory directory;

	/**
	 * 获取写索引实例
	 * 
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter() throws Exception {
		directory = FSDirectory.open(Paths.get(indexDir));
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();// 中文分词器
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, iwc);
		return writer;
	}

	/**
	 * 对博客添加索引
	 * 
	 * @param blog
	 * @throws Exception
	 */
	public void addIndex(Blog blog) throws Exception {
		IndexWriter writer = getWriter();
		Document document = new Document();
		document.add(new Field("id", String.valueOf(blog.getId()), StringField.TYPE_STORED));
		document.add(new Field("title", blog.getTitle(), TextField.TYPE_STORED));
		document.add(new Field("releaseDate",
				DateUtil.formatToString(new Date(), "yyyy月MM月dd日 HH:mm"), StringField.TYPE_STORED));
		document.add(new Field("content", blog.getContentWithNoHTMLTag(), TextField.TYPE_STORED));
		writer.addDocument(document);
		writer.close();
	}

	/**
	 * Lucene查询博客
	 * 
	 * @param queryStr
	 * @return
	 * @throws Exception
	 */
	public List<Blog> searchBlog(String queryStr) throws Exception {
		// 通过directory获取indexSearcher
		directory = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher is = new IndexSearcher(reader);

		// 中文分词器
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

		// 中文查询条件一：title
		QueryParser parser1 = new QueryParser("title", analyzer);
		Query query1 = parser1.parse(queryStr);

		// 中文查询条件二：content
		QueryParser parser2 = new QueryParser("content", analyzer);
		Query query2 = parser2.parse(queryStr);

		// 组合上面两个条件
		BooleanQuery booleanQuery = new BooleanQuery.Builder().add(query1, Occur.SHOULD)
				.add(query2, Occur.SHOULD).build();

		// 获取100条最佳记录
		TopDocs hits = is.search(booleanQuery, 100);

		// 对查询条件一评分，构建高亮器
		QueryScorer scorer = new QueryScorer(query1);
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>",
				"</font></b>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);

		List<Blog> blogList = new LinkedList<Blog>();
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			// 获取文档
			Document doc = is.doc(scoreDoc.doc);

			Blog blog = new Blog();
			blog.setId(Integer.parseInt(doc.get("id")));
			blog.setReleaseDateStr(doc.get("releaseDate"));

			// 设置经高亮器处理后的title
			String title = doc.get("title");
			String hTitle = highlighter.getBestFragment(analyzer, "title", title);// 通过高亮容器获取高亮文本
			if (StringUtil.isNotEmpty(hTitle))
				blog.setTitle(hTitle);
			else
				blog.setTitle(title);

			// 设置经高亮器处理后的content
			String content = doc.get("content");
			String hContent = highlighter.getBestFragment(analyzer, "content", content);// 通过高亮容器获取高亮文本
			if (StringUtil.isNotEmpty(hContent))
				blog.setContent(hContent);
			else {
				if (content.length() <= 100)
					blog.setContent(content);
				else
					blog.setContent(content.substring(0, 100) + "...");
			}
			blogList.add(blog);
		}

		return blogList;
	}

	/**
	 * 删除指定博客的索引
	 * 
	 * @param blogId
	 * @throws Exception
	 */
	public void deleteIndex(String blogId) throws Exception {
		IndexWriter writer = getWriter();
		writer.deleteDocuments(new Term("id", blogId));
		writer.forceMergeDeletes();
		writer.commit();
		writer.close();
	}

	/**
	 * 更新博文索引
	 * 
	 * @param blog
	 * @throws Exception
	 */
	public void updateIndex(Blog blog) throws Exception {
		IndexWriter writer = getWriter();
		Document document = new Document();
		document.add(new Field("id", String.valueOf(blog.getId()), StringField.TYPE_STORED));
		document.add(new Field("title", blog.getTitle(), TextField.TYPE_STORED));
		document.add(new Field("releaseDate",
				DateUtil.formatToString(blog.getReleaseDate(), "yyyy月MM月dd日 HH:mm"),
				StringField.TYPE_STORED));// 从数据库获取日期，更新时保持发布日期不变
		document.add(new Field("content", blog.getContentWithNoHTMLTag(), TextField.TYPE_STORED));
		writer.updateDocument(new Term("id", String.valueOf(blog.getId())), document);
		writer.close();
	}
}
