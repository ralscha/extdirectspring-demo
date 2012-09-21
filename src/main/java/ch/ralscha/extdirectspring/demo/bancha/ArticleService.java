package ch.ralscha.extdirectspring.demo.bancha;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@Service
public class ArticleService {

	@Autowired
	private UserService userService;

	private final static Map<Integer, Article> articleDb = Maps.newConcurrentMap();

	private final static AtomicInteger maxId = new AtomicInteger(35);

	@PostConstruct
	public void init() {
		for (int i = 0; i < 35; i++) {
			Article a = new Article();
			a.setId(i + 1);
			a.setTitle("Title " + (i + 1));
			a.setBody("This is the body " + (i + 1));
			a.setDate(new DateTime(2012, 11, i % 30 + 1, 11, 51, 55));
			a.setPublished(i % 3 == 0);
			a.setUserId((i % 4) + 1);
			articleDb.put(a.getId(), a);
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_READ, group = "bancha")
	public List<Article> read() {
		return ImmutableList.copyOf(articleDb.values());
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<Article> create(List<Article> newArticles) {
		ImmutableList.Builder<Article> result = ImmutableList.builder();
		for (Article article : newArticles) {
			article.setId(maxId.incrementAndGet());
			articleDb.put(article.getId(), article);
			result.add(article);
		}
		return result.build();
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public List<Article> update(List<Article> updatedArticles) {
		for (Article article : updatedArticles) {
			articleDb.put(article.getId(), article);
		}
		return updatedArticles;
	}

	@ExtDirectMethod(value = ExtDirectMethodType.STORE_MODIFY, group = "bancha")
	public void destroy(List<Article> destroyArticles) {
		for (Article article : destroyArticles) {
			articleDb.remove(article.getId());
		}
	}

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST, group = "bancha")
	public ExtDirectFormPostResult submit(Article article) {
		article.setId(maxId.incrementAndGet());
		articleDb.put(article.getId(), article);

		return new ExtDirectFormPostResult(true);
	}
}
