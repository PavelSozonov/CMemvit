package ru.innopolis.lips.memvit.model;

/*
 * Represent state of all memory on a particular execution step
 */
public interface State {

	void setData(String data);
	
	String getData();
}
