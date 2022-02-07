package com.gisa.gisacore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
public class JwtTokenUtil {

	public static final String CLAIM_ROLES = "roles";
	public static final String CLAIM_ISS = "iss";

    private final String secret;

	private final Integer expiresIn;

	public String generate(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(CipherUtil.encrypt32(subject))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (expiresIn * 1000)))
				.signWith(SignatureAlgorithm.HS512,
						Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8)))
				.compact();
	}

	public Boolean validate(String token, String associateId) {
		final String encryptedAssociateId = CipherUtil.encrypt64(associateId);
		final String subject = getSubject(token);
		return (subject.equals(associateId) && !isExpired(token));
	}

    public String getSubject(String token) {
        return getClaim(token, Claims::getSubject);
    }

	public Boolean isExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date());
	}

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
    }

	public boolean verifyContainRole(String token, String... roles) {
		final Claims claims = getAllClaims(token);
		List<String> rolesJwt = claims.get(CLAIM_ROLES, List.class);
		for(String role : roles) {
			if(rolesJwt.contains(role)) {
				return true;
			}
		}
		return false;
	}
}