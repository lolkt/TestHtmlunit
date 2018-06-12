package com.lolkt.html;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestHtmlUnit {

	public static void main(String[] args)   {
		// TODO Auto-generated method stub
		String url = "http://live.500.com/";
		String pageXml = getHtmlSource(url);
		getBeanBy500(pageXml);
	}

	private static String getHtmlSource(String url)  {
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

		if(page==null) return "";
//		String res = page.asText();
		String pageXml = page.asXml();
		return pageXml;
	}

	public static void getBeanBy500(String html) {
 
		Document doc = Jsoup.parse(html);
		Elements table = doc.getElementsByClass("bf_tablelist01");
		Elements rows = table.select("tbody").get(0).children();

		for (Element link : rows) {
			System.out.println("11111  " + link.select("td").get(0).text());
			Element spans = link.getElementsByClass("bf_op").first();
			System.out.println(" " + spans.child(0).text());
			System.out.println(" " + spans.child(1).text());
			System.out.println(" " + spans.child(2).text());
		}
	}
}