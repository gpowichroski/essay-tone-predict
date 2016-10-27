package com.ibm.toneanalysis.model;

import com.ibm.toneanalysis.service.WatsonUserModeller;
import com.ibm.toneanalysis.model.TonePerEssay;

/** Models the JSON received from an POST /api/add request. **/
public class AddEssayRequest
{
	public String teacher;
	public String classs;
	public String grade;
	public String essay;
	public TonePerEssay tone;

	public static Essay toEssay(AddEssayRequest apr)
	{
		Essay p = new Essay(apr.teacher, apr.classs, apr.grade, apr.essay);
		TonePerEssay tone = getToneAnalysis(apr);
		p.setTone(tone);
		return p;
	}
	
	private static TonePerEssay getToneAnalysis(AddEssayRequest apr)
	{
		// get tones
		TonePerEssay tpe = new TonePerEssay();
		WatsonUserModeller WUM = new WatsonUserModeller();
		String tone = WUM.getTonesEasyList(apr.essay);
		tpe.setRequestText(apr.essay);
		tpe.setResponseText(tone);
		return tpe;
	}
	
	/*private static ResumeInfo constructResumeInfo(AddEssayRequest apr)
	{
		// construct ResumeInfo
		ResumeInfo ri = new ResumeInfo();
		ri.pastEmployers = apr.resumeInfo.get("pastEmployers");
		ri.techSkills = apr.resumeInfo.get("techSkills");
		return ri;
	}*/

}
