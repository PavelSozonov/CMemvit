package ru.innopolis.lips.memvit.Model;

public class StateImpl implements State {

	private String data;
	
	public StateImpl(String data) {
		this.data = data;
	}
	
	@Override
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String getData() {
		return data;
	}

}
