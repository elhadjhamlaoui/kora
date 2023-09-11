package com.app_republic.shoot.model.general;

import java.util.List;

public class ApiResponse{
	private List<Match> response;
	private String get;
	private Paging paging;
	private Parameters parameters;
	private int results;
	private List<Object> errors;

	public void setResponse(List<Match> response){
		this.response = response;
	}

	public List<Match> getResponse(){
		return response;
	}

	public void setGet(String get){
		this.get = get;
	}

	public String getGet(){
		return get;
	}

	public void setPaging(Paging paging){
		this.paging = paging;
	}

	public Paging getPaging(){
		return paging;
	}

	public void setParameters(Parameters parameters){
		this.parameters = parameters;
	}

	public Parameters getParameters(){
		return parameters;
	}

	public void setResults(int results){
		this.results = results;
	}

	public int getResults(){
		return results;
	}

	public void setErrors(List<Object> errors){
		this.errors = errors;
	}

	public List<Object> getErrors(){
		return errors;
	}
}