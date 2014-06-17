package com.prodyna.pac.acres.reservation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;

import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.common.logging.Logged;
import com.prodyna.pac.acres.common.monitoring.Monitored;
import com.prodyna.pac.acres.common.security.Unsecured;
import com.prodyna.pac.acres.user.User;
import com.prodyna.pac.acres.user.UserService;

@Unsecured
@Stateless
@Logged
@Monitored
public class ReservationServiceBean implements ReservationService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	@Unsecured
	private UserService userService;

	@Inject
	@Unsecured
	private AircraftService aircraftService;

	@Override
	public Reservation readReservation(long id) {
		return em.find(Reservation.class, id);
	}

	@Override
	public List<Reservation> readAllReservations() {
		return em.createQuery("select e from Reservation e", Reservation.class).getResultList();
	}

	@Override
	public Reservation createReservation(Reservation reservation) {
		em.persist(reservation);
		return reservation;
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		return em.merge(reservation);
	}

	@Override
	public void deleteReservation(long id) {
		em.remove(em.find(Reservation.class, id));
	}

	@Override
	public List<Reservation> findReservations(String login, String aircraftRegistration) {
		User user = null;
		if (login != null) {
			user = userService.findUser(login);
		}
		Aircraft aircraft = null;
		if (aircraftRegistration != null) {
			aircraft = aircraftService.findAircraft(aircraftRegistration);
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> reservation = cq.from(Reservation.class);
		List<Predicate> predicates = new ArrayList<>();
		if (user != null) {
			predicates.add(cb.equal(reservation.get("user"), user));
		}
		if (aircraft != null) {
			predicates.add(cb.equal(reservation.get("aircraft"), aircraft));
		}
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		List<Reservation> result = em.createQuery(cq).getResultList();
		return result;
	}
}
