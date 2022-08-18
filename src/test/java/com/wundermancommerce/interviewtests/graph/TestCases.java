package com.wundermancommerce.interviewtests.graph;

import static org.junit.Assert.*;  

import java.util.List;

import org.junit.Test;  

public class TestCases {
	
	
	@Test
	public void testGetAllPeople() {
	    ReadPersonAndRelationship r = new ReadPersonAndRelationship();
        //Exercise 1
	    List<People> allPeople = r.getAllPeople();
        //Exercise 2 
	    assertEquals(12, allPeople.size());
	}
	
	//Exercise 3
	@Test
	public void testGetRelationshipCount() {
	    ReadPersonAndRelationship r = new ReadPersonAndRelationship();

	    int bobRelationshipsCount = r.getRelationShipCount("Bob");
	    int jennyRelationshipsCount = r.getRelationShipCount("Jenny");

	    assertEquals(4, bobRelationshipsCount);
	    assertEquals(3, jennyRelationshipsCount);
	}

	//Exercise 4
	@Test
	public void testExtendedFamily() {
		
		 ReadPersonAndRelationship r = new ReadPersonAndRelationship();
		 int count = r.getExtendedFamilyCount("Bob");
		 assertEquals(4, count);
		 
		 int Jcount = r.getExtendedFamilyCount("Jenny");
		 assertEquals(4, Jcount);
	}		
	
	
}