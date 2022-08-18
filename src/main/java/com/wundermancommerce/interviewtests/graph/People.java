package com.wundermancommerce.interviewtests.graph;

import java.util.ArrayList;
import java.util.List;

public class People {
	
	private String name;
	private String email;
	private int age;
	boolean visited = false;
	private List<Relatives> relations = new ArrayList<>();

	public People(String name, String email, int age) {
		this.name = name;
		this.email = email;
		this.age = age;
	}

	public int getRelationShipCount() {
		return this.relations.size();
	}

	public void addRelationShip(People people, String relationShip) {
		if (this.getRelationShipCount() > 0) {
			this.getRelations().forEach(rel -> {
				// if user already in the relationship list then don't add the user in the
				// relations
				if (rel.getPerson().getEmail().equals(people.getEmail())) {
					return;
				}
			});
		}
		Relatives newRelationShip = new Relatives(Relation.valueOf(relationShip), people);
		this.relations.add(newRelationShip);
		// Relation is bi-directional so add this to the relation of person
		newRelationShip = new Relatives(Relation.valueOf(relationShip), this);
		people.getRelations().add(newRelationShip);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Relatives> getRelations() {
		return relations;
	}

	public void setRelations(List<Relatives> relations) {
		this.relations = relations;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public String toString() {
		return "name: " + this.getName() + " email: " + this.getEmail() + " age: " + this.getAge();
	}
}