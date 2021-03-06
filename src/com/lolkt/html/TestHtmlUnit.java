package com.lolkt.html;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestHtmlUnit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//		 updateOddsBy500();
	updateOddsBySporttery();
	}

	public static void updateOddsBy500() {
		// TODO Auto-generated method stub
		String url = "http://live.500.com/";
		String pageXml = getHtmlSource(url);
		List<Map> list = getBeanBy500(pageXml);
		System.out.println("333 " + list.toString());
	}

	public static void updateOddsBySporttery() {
		String url = "http://info.sporttery.cn/livescore/fb_livescore.html";
		String pageXml = getHtmlSource(url);
		List<Map> list = getBeanBySporttery(pageXml);
		System.out.println("333 " + list.toString());
	}

	private static String getHtmlSource(String url) {
		// 初始化浏览器对象
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		// 配置是否加载css和javaScript
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setTimeout(50000);
		webClient.getOptions().setUseInsecureSSL(true);

		HtmlPage page = null;
		try {
			page = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (page == null)
			return "";
		// String res = page.asText();
		String pageXml = page.asXml();
		return pageXml;
	}

	public static List<Map> getBeanBy500(String html) {

		Document doc = Jsoup.parse(html);
		Elements table = doc.getElementsByClass("bf_tablelist01");
		Elements rows = table.select("tbody").get(0).children();

		List<Map> list = new ArrayList();
		if (rows == null) {
			return list;
		}

		for (Element link : rows) {
			String title = link.select("td").get(0).text();
		 
			Element spans = link.getElementsByClass("bf_op").first();
			String odds = spans.child(0).text() + "," + spans.child(1).text()
					+ "," + spans.child(2).text();
			Map map = new HashMap();
			map.put("title", title);
			map.put("odds", odds);
			list.add(map);
		}

		return list;
	}

	public static List<Map> getBeanBySporttery(String html) {
		
		List<Map> list = new ArrayList();

		Document doc = Jsoup.parse(html);
		Element table = doc.getElementById("tbl_match_list");
		if (table == null) {
			return list;
		}
		
		Elements rows = table.select("tbody").get(0).children();
		if (rows == null) {
			return list;
		}

		for (Element link : rows) {

			Element select = link.getElementsByClass("boder_B").first();
			if (select != null) {
				String title = link.select("td").get(0).text();

				Elements spans = select.getElementsByClass("u-nobg");
				if (spans != null && spans.get(0) != null
						&& spans.get(1) != null && spans.get(2) != null) {
					String odds = spans.get(0).text() + ","
							+ spans.get(1).text() + "," + spans.get(2).text();
					Map map = new HashMap();
					map.put("title", title);
					map.put("odds", odds);
					list.add(map);
				}
			}
		}

		return list;
	}
}
