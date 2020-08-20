package com.fangyu3.webquiz.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="quiz")
public class Quiz {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name="title")
	private String title;
	
	@NotNull
	@Column(name="text")
	private String text;
	
	@Size(min=2)
	@NotNull
	@Column(name="options")
	private String[] options;
	
	@Column(name="answer")
	private int[] answer;

	public Quiz() {
		// TODO Auto-generated constructor stub
	}
	
	public Quiz(int id,String title, String text, String[] options, int[] answer) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.options = options;
		this.answer = answer;
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
	public int[] getAnswer() {
		return answer;
	}
	
	@JsonProperty
	public void setAnswer(int[] answer) {
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
				+ ", answer=" + Arrays.toString(answer) + "]";
	}

	
}
