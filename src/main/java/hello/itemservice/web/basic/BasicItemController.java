package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) { // 생성자 주입
//        this.itemRepository = itemRepository;
//    } >> @RequiredArgsConstructor


    @GetMapping // @RequestMapping("/basic/items")
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add") // 등록 Form 으로 이동
    public String addForm(){

        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                            @RequestParam int price,
//                            @RequestParam Integer quantity,
//                            Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//        itemRepository.save(item);
//        model.addAttribute("item", item);
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item ,Model model) { // Model 객체 생략가능
//
//        // @ModelAttribute 가 아래 코드 역할을 해준다
////        Item item = new Item();
////        item.setItemName(itemName);
////        item.setPrice(price);
////        item.setQuantity(quantity);
//
//        itemRepository.save(item);
////        model.addAttribute("item", item); // 주석처리해도 ok - @ModelAttribute 가 지정한 객체를 자동으로 넣어준다.
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item){ // @ModelAttribute 의 이름 생략가능
//        itemRepository.save(item);
//        return "basic/item";
//    }


//    @PostMapping("/add") // 실제 등록
    public String addItemV4(Item item) { // @ModelAttribute 자체도 생략가능
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add") // 실제 등록
    public String addItemV5(Item item) { // PRG - Post/Redirect/Get 문제 해결
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add") // 실제 등록
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) { // URL 에 변수를 더하는 것은 인코딩 문제로 위험 - RedirectAttribute 로 해결
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true); // 쿼리 파라미터

        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit") // 상품 수정 폼
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit") // 상품 수정 처리
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; // 저장 후 '상품상세'로 redirect
    }




        // 테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }


}
