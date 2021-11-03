package br.com.meutudo.banksystem.service.impl;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meutudo.banksystem.model.Token;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.repository.TokenRepository;
import br.com.meutudo.banksystem.repository.UserRepository;
import br.com.meutudo.banksystem.service.TokenService;
import br.com.meutudo.banksystem.service.UserService;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UserService userService;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Token createToken(long userId) {
		Token token = new Token();
		User user = this.userService.getUserById(userId);
		if (user != null) {
			StringBuilder stringBuilder = new StringBuilder();
			long currentTimeInMilisecond = Instant.now().toEpochMilli();
			token.setToken(stringBuilder.append(currentTimeInMilisecond).append("-")
					.append(UUID.randomUUID().toString()).toString());
			token.setUser(user);
			token.setCreationDate(new Date());
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			c.add(Calendar.DATE, 1);
			now = c.getTime();
			token.setExpirationDate(now);
			return tokenRepository.save(token);
		}
		return null;
	}

	@Override
	public User validateToken(String token) {
		Query query = em.createQuery("SELECT user_id FROM TOKEN WHERE token = ?1 AND expiration_date > CURRENT_TIMESTAMP()");
		List<Long> ids = query.setParameter(1, token).getResultList();
		User user = userService.getUserById(ids.get(0));
		return user;
	}
}
