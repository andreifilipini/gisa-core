package com.gisa.gisacore.util;

import com.gisa.gisacore.model.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

	public static final String CLAIM_ROLES = "roles";
	public static final String CLAIM_ISS = "iss";
	public static final String BEARER_REPLACE = "Bearer ";

	@Value("${jwt.secret}")
    private String secret;

	@Value("${jwt.expiresIn}")
	private Integer expiresIn;

	public String generate(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(CipherUtil.encrypt32("us_".concat(subject)))
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
		if(token != null && token.contains(BEARER_REPLACE)) {
			token = token.replace(BEARER_REPLACE, "");
		}
        return Jwts.parser()
				.setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
    }

	public boolean verifyContainRole(String token, RoleEnum... roles) {
		final Claims claims = getAllClaims(token);
		List<String> rolesJwt = claims.get(CLAIM_ROLES, List.class);
		for(RoleEnum role : roles) {
			if(rolesJwt.contains(role.name())) {
				return true;
			}
		}
		return false;
	}

	public String getJwtToken(HttpServletRequest request) {
		HttpServletRequest req = request;
		String token = req.getHeader("Authorization");

		return getJwtToken(token);
	}

	public String getJwtToken(String authorization) {
		if(StringUtil.isNotBlank(authorization)) {
			return authorization.replace("Bearer ",  "");
		}
		return null;
	}
}