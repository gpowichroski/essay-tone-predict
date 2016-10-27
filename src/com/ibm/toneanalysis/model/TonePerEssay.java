package com.ibm.toneanalysis.model;

/*
 * Note Tones are from the Watson API
 */

public class TonePerEssay 
{

	public EmotionTone eTone;
	public LanguageTone lTone;
	public SocialTendenciesTone stTone;	
	public String toneText;
	public double sentenceId;	
	public String responseText;
	public String requestText;
	
	public String getRequestText() {
		return requestText;
	}
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	public String getToneText() {
		return toneText;
	}
	public void setToneText(String toneText) {
		this.toneText = toneText;
	}
	public double getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(double sentenceId) {
		this.sentenceId = sentenceId;
	}
	public EmotionTone geteTone() {
		return eTone;
	}
	public void seteTone(EmotionTone eTone) {
		this.eTone = eTone;
	}
	public LanguageTone getlTone() {
		return lTone;
	}
	public void setlTone(LanguageTone lTone) {
		this.lTone = lTone;
	}
	public SocialTendenciesTone getStTone() {
		return stTone;
	}
	public void setStTone(SocialTendenciesTone stTone) {
		this.stTone = stTone;
	}




}
