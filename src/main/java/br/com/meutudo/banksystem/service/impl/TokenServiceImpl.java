package br.com.meutudo.banksystem.service.impl;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meutudo.banksystem.model.Token;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.repository.TokenRepository;
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
			return this.tokenRepository.save(token);
		}
		return null;
	}

	@Override
	public User validateToken(String token) {
		Query query = em.createQuery(
				"SELECT t FROM TOKEN t	WHERE token = :token AND expirationDate > CURRENT_TIMESTAMP()", Token.class);
		query.setParameter("token", token);
		try {
			Token t = (Token) query.getSingleResult();
			User user = this.userService.getUserById(t.getUser().getId());
			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception ee) {
			return null;
		}
	}
}
