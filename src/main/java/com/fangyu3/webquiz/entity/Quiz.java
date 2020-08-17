package com.fangyu3.webquiz.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Quiz {
	
	private int id;
	private String title;
	private String text;
	private String[] options;
	private int answer;

	public Quiz() {
		// TODO Auto-generated constructor stub
	}
	
	public Quiz(int id,String title, String text, String[] options, int answer) {
		this.setId(id);
		this.title = title;
		this.text = text;
		this.options = options;
		this.setAnswer(answer);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}
	
	@JsonIgnore
	public int getAnswer() {
		return answer;
	}
	
	@JsonProperty
	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", text=" + text + ", options=" + Arrays.toString(options)
				+ ", answer=" + answer + "]";
	}

	
}
