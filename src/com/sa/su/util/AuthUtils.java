package com.sa.su.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sa.su.model.Usuario;

public final class AuthUtils {
	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	private static final String TOKEN_SECRET = "P4g055ucur541";
	
	public static String createToken(Usuario user) throws Exception {
		JWTClaimsSet claim = new JWTClaimsSet(); 
		claim.setSubject(user.getLegajo());
		claim.setCustomClaim("legajo", user.getLegajo());
		claim.setCustomClaim("nombre", user.getNombre());
		claim.setCustomClaim("apellido", user.getApellido());
		claim.setCustomClaim("funciones", new ObjectMapper().writeValueAsString(user.getFunciones()));
		claim.setCustomClaim("centro_costos", new ObjectMapper().writeValueAsString(user.getCentroCostos()));
		
		JWSSigner signer = new MACSigner(TOKEN_SECRET);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);
		
		return jwt.serialize();
	}
}
