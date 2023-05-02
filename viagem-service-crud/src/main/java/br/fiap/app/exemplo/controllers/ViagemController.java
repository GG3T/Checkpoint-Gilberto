package br.fiap.app.exemplo.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import br.fiap.app.exemplo.service.CalculoViagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.fiap.app.exemplo.models.Viagem;
import br.fiap.app.exemplo.repositories.ViagemRepository;

@Controller
@RequestMapping("/viagem")
public class ViagemController {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    CalculoViagemService calculoViagemService;


    @GetMapping("/index")
    public ModelAndView get() {
        ModelAndView model = new ModelAndView("viagem/index");

        List<Viagem> listaViagem = viagemRepository.findAll();
        model.addObject("viagens", listaViagem);

        return model;

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Viagem> putUsuario(@PathVariable Long id, @Valid @RequestBody Viagem viagemAtualizada) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada para o ID informado: " + id));

        final Viagem viagemAtualizadaBD = viagemRepository.save(viagem);
        return ResponseEntity.ok(viagemAtualizadaBD);
    }


    @PostMapping("/edit/{id}")
    public String editUsuario(@PathVariable("id") Long id, @ModelAttribute("viagem") Viagem objViagem, Model model) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada para o ID informado: " + id));

        viagemRepository.save(viagem);
        model.addAttribute("viagem", viagem);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        viagemRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String create() {
        return "viagem/create";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Viagem viagem = viagemRepository.findById(id).orElse(null);
        model.addAttribute("viagem", viagem);
        return "viagem/edit";
    }

    @GetMapping("/editarViagem/{id}")
    public String editViagem(Model model, @PathVariable("id") Long id) {
        Optional<Viagem> viagem = viagemRepository.findById(id);
        model.addAttribute("viagem", viagem);
        return "viagem/editarViagem";
    }

    @GetMapping("/indexViagem")
    public ModelAndView getViagem() {
        ModelAndView model = new ModelAndView("viagem/indexViagem");
        List<Viagem> listaViagem = viagemRepository.findAll();
        model.addObject("viagens", listaViagem);
        return model;
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("viagem") @Valid @RequestBody Viagem viagem, Model model) throws ParseException {
        var dtRetorno = calculoViagemService.calculate(viagem.getEstadia(), viagem.getDtoDecolagem());

        viagem.setDtoRetorno(dtRetorno);

        viagemRepository.save(viagem);
        return "redirect:/";
    }


}
