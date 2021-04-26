package controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;

import javax.jws.WebParam;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public ModelAndView listCustomer() {
        List<Customer> customerList = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("listCustomer", customerList);
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detailCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/detail");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getFormEdit(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("editForm", customer);
        return modelAndView;
    }

    @PostMapping("/edit")
    public String editCustomer(@ModelAttribute("editForm") Customer customer, Model model) {
        if (customer.getName() == null || customer.getName().trim().equals("") && customer.getAddress().trim().equals("")) {
            model.addAttribute("status", "invalid  try again");
            return "/edit";
        }
        System.out.println("name"+customer.getName());
        customerService.save(customer);
        return "redirect:/";
    }
}
