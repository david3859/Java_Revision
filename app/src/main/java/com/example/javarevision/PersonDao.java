package com.example.javarevision;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PersonDao implements Dao<Person> {
    
    private List<Person> persons = new ArrayList<>();
    
    public PersonDao() {
       
    }
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Person> get(long id) {
        return Optional.ofNullable(persons.get((int) id));
    }
    
    @Override
    public List<Person> getAll() {
        return persons;
    }

    public int lastIndex() {
    	return persons.size()-1;
    }
    
    public int nbrePerson() {
    	return persons.size();
    }
    
   public int beforeLastIndex(){
	   if(persons.size()<2) {
		  return 0;
	   }else {
		   return persons.size()-2; 
	   }
    }
    
    @Override
    public void save(Person person) {
        persons.add(person);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void update(Person person, String[] params) {
        person.setName(Objects.requireNonNull(
          params[0], "Name cannot be null"));
        person.setEmail(Objects.requireNonNull(
          params[1], "Email cannot be null"));
       
        
        persons.add(person);
    }
    
    @Override
    public void delete(Person person) {
        persons.remove(person);
        //System.out.println("Person delete Successfuly!");
    }

    public void  deleteAllP(){
        Collection<?> collection = persons;
        persons.removeAll(collection);
    }

	
}
