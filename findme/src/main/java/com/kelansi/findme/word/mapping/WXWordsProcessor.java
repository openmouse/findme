package com.kelansi.findme.word.mapping;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

import antlr.TokenStream;

public class WXWordsProcessor {

	public void parse(String words){
		Analyzer analyzer = new IKAnalyzer(true);
		StringReader reader = new StringReader(words);
	}
	
}
