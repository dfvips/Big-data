package com.zdata.util;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ListUtils implements Comparator<Integer> {
	
	public static void main(String[] args) {
		int[] nums = new int[] { 6, 4, -3, 5, -2, -1, 0, 1, -9 };
		// 定义数组待存入
		List<Integer> afterNum = new LinkedList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			afterNum.add(nums[i]);
		}
		Collections.sort(afterNum,new ListUtils());
		for (int i = 0; i < afterNum.size(); i++) {
			System.out.print(afterNum.get(i)+" ");
		}
	}

	@Override
	public int compare(Integer o1, Integer o2) {
		if (o2 < o1) {
			return 1;
		}
		if (o2 > o1) {
			return -1;
		}
		return 0;
	}
}
