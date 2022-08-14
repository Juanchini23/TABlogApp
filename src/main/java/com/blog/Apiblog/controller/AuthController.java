package com.blog.Apiblog.controller;

import com.blog.Apiblog.dto.LoginDto;
import com.blog.Apiblog.dto.RegistroDto;
import com.blog.Apiblog.model.Rol;
import com.blog.Apiblog.model.Usuario;
import com.blog.Apiblog.repository.RolRepository;
import com.blog.Apiblog.repository.UsuarioRepository;
import com.blog.Apiblog.seguridad.JwtAuthResponseDto;
import com.blog.Apiblog.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repoUsuario;

    @Autowired
    private RolRepository repoRol;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //obtenemos el token
        String token = jwtTokenProvider.generarTokenDeAcceso(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDto registroDto){

        if(repoUsuario.existsByUsername(registroDto.getUsername())){
            return new ResponseEntity<>("El nombre de usuario ya esta en uso", HttpStatus.BAD_REQUEST);
        }
        if(repoUsuario.existsByEmail(registroDto.getEmail())){
            return new ResponseEntity<>("El email ya esta en uso", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDto.getNombre());
        usuario.setUsername(registroDto.getUsername());
        usuario.setEmail(registroDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDto.getPassword()));

        Rol roles = repoRol.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        repoUsuario.save(usuario);

        return new ResponseEntity<>("Usuario registrado con exito", HttpStatus.OK);
    }
}
