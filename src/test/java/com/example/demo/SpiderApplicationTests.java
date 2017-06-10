package com.example.demo;

import com.example.demo.dao.repository.TestRepository;
import com.example.demo.pipeline.*;
import com.example.demo.processor.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {
	@Autowired
	private TestRepository testRepository;

	@Autowired
	private HtmlPageProcessor htmlPageProcessor;

	@Autowired
	private HtmlPipeline htmlPipeline;

	@Autowired
	private LeagueDataProcessor leagueDataProcessor;

	@Autowired
	private LeagueDataPipeline leagueDataPipeline;

	@Autowired
	private SubLeagueDataProcessor subLeagueDataProcessor;

	@Autowired
	private SubLeagueDataPipeline subLeagueDataPipeline;

	@Autowired
	private CupDataProcessor cupDataProcessor;

	@Autowired
	private CupDataPipeline cupDataPipeline;

	@Autowired
	private DanChangDateProcessor danChangDateProcessor;

	@Autowired
	private DanChangDatePipeline danChangDatePipeline;

	@Test
	public void findUrl() {
		String encoding="GBK";
		File file = new File("C:\\Users\\gaojun\\Desktop\\spider\\leftData.txt");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		try {
			if(file.isFile() && file.exists()){ //判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file),encoding);//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt;
				while((lineTxt = bufferedReader.readLine()) != null){
					lineTxt = lineTxt.substring(lineTxt.indexOf(" = ")+3,lineTxt.indexOf(";"));
					System.out.println(lineTxt);
					try{
						Object object = objectMapper.readValue(lineTxt,List.class);
						saveArray(object);
						System.out.println(object);
					}catch (Exception e){
						System.out.println(e.getMessage());
					}

				}
				read.close();
			}else{
				System.out.println("找不到指定的文件");
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Test
	public void findJavaScript(){
		List<String> urlList = testRepository.queryUrl();
		Spider.create(htmlPageProcessor)
				.addUrl(urlList.toArray(new String[urlList.size()]))
				.addPipeline(htmlPipeline)
				.addUrl()
				.thread(5)
				.run();
	}

	@Test
	public void findLeagueData(){
		List<String> urlList = testRepository.queryLeagueUrl();
		Spider.create(leagueDataProcessor)
				.addUrl(urlList.toArray(new String[urlList.size()]))
				.addPipeline(leagueDataPipeline)
				.addUrl()
				.thread(5)
				.run();
	}

	@Test
	public void findSubLeagueData(){
		List<String> urlList = testRepository.querySubLeagueUrl();
		Spider.create(subLeagueDataProcessor)
				.addUrl(urlList.toArray(new String[urlList.size()]))
				.addPipeline(subLeagueDataPipeline)
				.thread(5)
				.run();
	}

	@Test
	public void findCupData(){
		List<String> urlList = testRepository.queryCupUrl();
		Spider.create(cupDataProcessor)
				.addUrl(urlList.toArray(new String[urlList.size()]))
				.addPipeline(cupDataPipeline)
				.thread(5)
				.run();
	}

	@Test
	public void findDanChangDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Date beginDate = new Date();
		Date endDate = new Date();
		try {
			beginDate = simpleDateFormat.parse("2009-05");
			endDate = simpleDateFormat.parse("2017-06");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar date = Calendar.getInstance();//定义日期实例
		date.setTime(beginDate);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyMM");
		List<String> urlList = new ArrayList<>();
		while (date.getTime().before(endDate)) {
			for (int i = 1; i < 20; i++) {
				String url = "http://trade.500.com/bjdc/?expect=" + simpleDateFormat2.format(date.getTime()) + "0" + i;
				urlList.add(url);
			}
			date.add(Calendar.MONTH, 1);
		}
		Spider.create(danChangDateProcessor)
				.addUrl(urlList.toArray(new String[urlList.size()]))
				.addPipeline(danChangDatePipeline)
				.thread(5)
				.run();
	}




	public void saveArray(Object object){
		List mainList = (List)object;
		List<com.example.demo.dao.entity.Test> testList = new ArrayList<>();
		for(Object main:mainList){
			List mainList2 = (List)main;
			List leagueList = (List)mainList2.get(4);
			for(Object league:leagueList){
				List league2 = (List)  league;
				com.example.demo.dao.entity.Test test1 = new com.example.demo.dao.entity.Test();
				test1.setA((String) mainList2.get(0));
				test1.setB(String.valueOf(mainList2.get(3)));
				test1.setC(String.valueOf(league2.get(0)));
				test1.setD(String.valueOf(league2.get(1)));
				test1.setE(String.valueOf(league2.get(2)));
				test1.setF(String.valueOf(league2.get(3)));
				test1.setG(String.valueOf(league2.get(4)));
				testList.add(test1);
			}
			List cupList = (List)mainList2.get(5);
			for(Object cup:cupList){
				List cup2 = (List)  cup;
				com.example.demo.dao.entity.Test test2 = new com.example.demo.dao.entity.Test();
				test2.setA((String) mainList2.get(0));
				test2.setB(String.valueOf(mainList2.get(3)));
				test2.setC(String.valueOf(cup2.get(0)));
				test2.setD(String.valueOf(cup2.get(1)));
				test2.setE(String.valueOf(cup2.get(2)));
				test2.setF(String.valueOf(cup2.get(3)));
				test2.setG(String.valueOf(cup2.get(4)));
				testList.add(test2);
			}
		}

		testRepository.save(testList);
	}

}
