package com.ibm.toneanalysis.model;

public class Essay implements Comparable<Essay>
{
	public String teacher;
	public String classs;
	public String grade;
	public String essay;
	public TonePerEssay tone;
	
	static Essay queryEssay;

	public Essay(String teacher, String classs, String grade, String essay)
	{
		this.teacher = teacher;
		this.classs = classs;
		this.grade = grade;
		this.essay = essay;
	}
	
	public Essay() {
		// TODO Auto-generated constructor stub
	}


	public void setQueryEssay(Essay p)
	{
		this.queryEssay = p;
	}
	
	public TonePerEssay getTone() {
		return tone;
	}
	public void setTone(TonePerEssay tone) {
		this.tone = tone;
	}

	@Override
	public int compareTo(Essay arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
