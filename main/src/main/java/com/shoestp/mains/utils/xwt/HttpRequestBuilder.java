package com.shoestp.mains.utils.xwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

@Getter
@Slf4j
public class HttpRequestBuilder {

	public static final String request_method_post = "post";
	public static final String request_method_get = "get";

	public HttpUriRequest request = null;

	private String uri = "";
	private String method = "";
	private Map<String, String> querys = null;
	private Map<String, String> headers = null;

	public static HttpRequestBuilder create(String uri, String method) {
		HttpRequestBuilder builder = new HttpRequestBuilder();
		builder.uri = uri;
		builder.method = method;
		return builder;
	}

	public HttpRequestBuilder addParam(Map<String, String> params) {
		params.entrySet().forEach(entry->{
			addParam(entry.getKey(), entry.getValue());
		});
		return this;
	}

	public HttpRequestBuilder addParam(String name, String value) {
		if(querys == null) {
			querys = new HashMap<>();
		}
		querys.put(name, value);
		return this;
	}

	public HttpRequestBuilder addHeader(String name, String value) {
		if(headers == null) {
			headers = new HashMap<>();
		}
		headers.put(name, value);
		return this;
	}

	public HttpRequestBuilder addHeader(Map<String, String> headers) {
		headers.entrySet().forEach(entry->{
			addHeader(entry.getKey(), entry.getValue());
		});
		return this;
	}

	private void initRequest() {
		if (method.equalsIgnoreCase(request_method_get)) {
			String uri2 = uri;
			if(querys != null) {
				uri2 = uri + "?" + querys.entrySet().stream().map(entry -> {
					return entry.getKey() + "=" + entry.getValue();
				}).collect(Collectors.joining("&"));
			}
			request = new HttpGet(uri2);
		} else if (method.equalsIgnoreCase(request_method_post)) {
			request = new HttpPost(uri);
			HttpEntity entity = new UrlEncodedFormEntity(querys.entrySet().stream().map(entry -> {
				return new BasicNameValuePair(entry.getKey(), entry.getValue());
			}).collect(Collectors.toList()), Consts.UTF_8);
			((HttpPost) request).setEntity(entity);
		} else {
			throw new IllegalArgumentException("不支持[" + method + "]请求");
		}
		if(headers != null)
			headers.entrySet().forEach(entry->{
				request.addHeader(entry.getKey(), entry.getValue());
			});
	}

	public String fetchStringContent() throws ClientProtocolException, IOException {
		initRequest();
		CloseableHttpClient httpClient = null;
		BufferedReader reader = null;
		try {
			log.debug("request:"+request.getURI());
			CloseableHttpResponse response = HttpClients.createDefault().execute(request);
			InputStream input = response.getEntity().getContent();
			reader = new BufferedReader(new InputStreamReader(input, Charset.forName("utf-8")));
			StringBuilder sb = new StringBuilder();
			String buffer = null;
			while ((buffer = reader.readLine()) != null) {
				sb.append(buffer);
			}
			log.debug("response:"+sb);
			return sb.toString();
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public <T> T fetchStringContent(Class<T> valueType) throws ClientProtocolException, IOException {
		String content = fetchStringContent();
		return JsonWriter.oMapper.readValue(content, valueType);
	}

}
