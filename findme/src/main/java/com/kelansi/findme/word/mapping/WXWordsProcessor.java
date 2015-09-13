package com.kelansi.findme.word.mapping;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Component("wxWordsProcessor")
public class WXWordsProcessor {
	
	public List<String> parse(String words) throws IOException{
		StringReader reader = null;
		try{
			@SuppressWarnings("resource")
			Analyzer analyzer = new IKAnalyzer(true);
			reader = new StringReader(words);
			TokenStream ts = analyzer.tokenStream("", reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
			List<String> tokenWords = new ArrayList<String>();
			while(ts.incrementToken()){
				tokenWords.add(term.toString());
			}
			return tokenWords;
		}finally{
			if(reader != null){
				reader.close();
			}
		}
	}
	
}
