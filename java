import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite que o HTML acesse a API
public class PasswordController {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+~`|}{[]\\:;?><,./-=";

    @GetMapping("/gerar-senha")
    public String gerarSenha(
            @RequestParam(defaultValue = "12") int length,
            @RequestParam(defaultValue = "true") boolean lowercase,
            @RequestParam(defaultValue = "true") boolean uppercase,
            @RequestParam(defaultValue = "true") boolean numbers,
            @RequestParam(defaultValue = "true") boolean symbols) {

        StringBuilder charPool = new StringBuilder();
        SecureRandom random = new SecureRandom(); // Mais seguro que o Math.random()
        List<Character> passwordChars = new ArrayList<>();

        // Garante pelo menos um caractere de cada tipo selecionado e preenche o pool
        if (lowercase) {
            charPool.append(LOWERCASE);
            passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        }
        if (uppercase) {
            charPool.append(UPPERCASE);
            passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        }
        if (numbers) {
            charPool.append(NUMBERS);
            passwordChars.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        if (symbols) {
            charPool.append(SYMBOLS);
            passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }

        if (charPool.length() == 0) {
            return "Selecione pelo menos uma opção!";
        }

        // Preenche o restante do comprimento da senha
        while (passwordChars.size() < length) {
            passwordChars.add(charPool.charAt(random.nextInt(charPool.length())));
        }

        // Embaralha a lista para que os primeiros caracteres não sejam previsíveis
        Collections.shuffle(passwordChars);

        // Converte a lista de caracteres de volta para String
        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }
}
