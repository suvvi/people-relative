package com.wundermancommerce.interviewtests.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadPersonAndRelationship {

    public List<People> getAllPeople() {
        List<People> peopleList = new ArrayList<>();
        try (FileReader fr = new FileReader("src/test/resources/people.csv");
                BufferedReader br = new BufferedReader(fr);) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] peoples = line.split(",");
                People p = new People(peoples[0], peoples[1], Integer.valueOf(peoples[2]));
                peopleList.add(p);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return peopleList;
    }

    public List<Relatives> getAllPeopleRelationShip(List<People> listPeople) {
        List<Relatives> relationshipsList = new ArrayList<>();
        try (FileReader fr = new FileReader("src/test/resources/relationships.csv");
                BufferedReader br = new BufferedReader(fr);) {

            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue; // Skip blank lines
                }
                String[] rr = line.split(",");
                People currentPerson = getPersonByEmail(listPeople, rr[0]);
                People nextPerson = getPersonByEmail(listPeople, rr[2]);
                if (null != nextPerson && null != currentPerson) {
                    currentPerson.addRelationShip(nextPerson, rr[1]);
                }
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return relationshipsList;
    }

    private People getPersonByEmail(List<People> peopleList, String email) {
        List<People> listPeople = peopleList.stream().filter(people -> people.getEmail().equals(email))
                .collect(Collectors.toList());
        if (null != listPeople && !listPeople.isEmpty()) {
            return listPeople.get(0);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        ReadPersonAndRelationship r = new ReadPersonAndRelationship();
        List<People> allPeople = r.getAllPeople();
        System.out.println(allPeople.toString());

        allPeople.forEach(people -> {
            System.out.println(r.getRelationShipCount(people.getName()));
            System.out.println(people.getName() + " : " + r.getExtendedFamilyCount(people.getName()));
        });

    }

    public int getRelationShipCount(String name) {
        List<People> peopleList = getAllPeople();
        getAllPeopleRelationShip(peopleList);
        // should be based on the unique key as email
        List<People> peoples = peopleList.stream().filter(people -> people.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        if (null == peoples || peoples.size() > 1 || peoples.isEmpty()) {
            return 0;
        }
        return peoples.get(0).getRelationShipCount();
    }

    public int getExtendedFamilyCount(String name) {
        List<People> peopleList = getAllPeople();
        getAllPeopleRelationShip(peopleList);

        Deque<People> stack = new LinkedList<>();
        People start = peopleList.stream().filter(people -> people.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList()).get(0);
        if (null != start) {
            stack.push(start);
            start.setVisited(true);
        }
        int extendedFamilyCount = 0;
        while (!stack.isEmpty()) {
            People poped = stack.pop();
            extendedFamilyCount++;
            poped.getRelations().forEach(rel -> {
                if (!rel.getPerson().isVisited() && rel.getRelationShip().equals(Relation.FAMILY)) {
                    stack.push(rel.getPerson());
                    rel.getPerson().setVisited(true);
                }
            });
        }
        return extendedFamilyCount;
    }

}