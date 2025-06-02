package br.com.fiap.pluvia.services;

import br.com.fiap.pluvia.dto.EnderecoDTO;
import br.com.fiap.pluvia.dto.UsuarioDTO;
import br.com.fiap.pluvia.models.Endereco;
import br.com.fiap.pluvia.models.Usuario;
import br.com.fiap.pluvia.repositories.EnderecoRepository;
import br.com.fiap.pluvia.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;

    public Usuario cadastrar(UsuarioDTO dto) {
        EnderecoDTO enderecoDTO = dto.getEndereco();

        Endereco endereco = enderecoService.buscarOuCriarPorCep(enderecoDTO.getCep());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setEndereco(endereco);

        return usuarioRepository.save(usuario);
    }

    public Usuario registro(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        return usuario;
    }

}
