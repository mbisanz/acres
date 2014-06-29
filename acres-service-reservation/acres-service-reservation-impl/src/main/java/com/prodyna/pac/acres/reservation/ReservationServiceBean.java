package com.prodyna.pac.acres.reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;

import org.slf4j.Logger;

import com.prodyna.pac.acres.aircraft.Aircraft;
import com.prodyna.pac.acres.aircraft.AircraftService;
import com.prodyna.pac.acres.common.qualifier.Logged;
import com.prodyna.pac.acres.common.qualifier.Monitored;
import com.prodyna.pac.acres.common.qualifier.Unsecured;
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
		return em.createQuery("select e from Reservation e", Reservation.class)
				.getResultList();
	}

	@Override
	public Reservation createReservation(Reservation reservation) {
		validate(reservation);
		em.persist(reservation);
		return reservation;
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		Reservation existing = em.find(Reservation.class, reservation.getId());
		if (existing == null) {
			throw new NoResultException("Reservation does not exist");
		}
		validate(reservation);
		return em.merge(reservation);
	}

	@Override
	public void deleteReservation(long id) {
		Reservation existing = em.find(Reservation.class, id);
		if (existing == null) {
			throw new NoResultException("Reservation does not exist");
		}
		em.remove(existing);
	}

	@Override
	public List<Reservation> findReservations(String login,
			String aircraftRegistration, List<ReservationState> states) {
		User user = null;
		if (login != null) {
			user = userService.findUser(login);
			if (user == null) {
				return Collections.emptyList();
			}
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
		if (states != null && !states.isEmpty()) {
			List<Predicate> statePredicates = new ArrayList<>();
			for (ReservationState state : states) {
				statePredicates.add(cb.equal(reservation.get("state"), state));
			}
			predicates.add(cb.or(statePredicates
					.toArray(new Predicate[statePredicates.size()])));
		}
		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		List<Reservation> result = em.createQuery(cq).getResultList();
		return result;
	}

	private void validate(Reservation reservation) {
		if (reservation.getValidFrom() == null) {
			return;
		}
		if (reservation.getValidTo() == null) {
			return;
		}
		if (reservation.getValidFrom().compareTo(reservation.getValidTo()) > 0) {
			throw new ValidationException("Start date must be before end date");
		}
	}
}
