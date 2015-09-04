/**
 * 
 */
package com.yuqi.model;

/**
 * @author HaoR
 * 
 * @date 2015-8-18 обнГ9:57:52
 * 
 */
public enum EDirection {
	UP(0), DOWN(1), LEFT(2), RIGHT(3), LEFT_UP(4), LEFT_DOWN(5), RIGHT_UP(6), RIGHT_DOWN(7);

	private int direction;

	EDirection(int direction) {
		this.direction = direction;
	}

	public int getValue() {
		return this.direction;
	}
}
