package br.com.caelum.tarefas.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class GeraTabelas {

	public static void main(String[] args) {
		System.out.println("Inicio");
		EntityManagerFactory factory = Persistence.
				createEntityManagerFactory("tarefas");
		
		factory.close();
	}
}
