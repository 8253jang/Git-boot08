package org.coupang.controller;

import org.coupang.domain.WebBoard;
import org.coupang.persistence.CustomerCrudRepository;
import org.coupang.persistence.WebBoardRepository;
import org.coupang.vo.PageMaker;
import org.coupang.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.java.Log;


@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {
	
	@Autowired
	private WebBoardRepository repo;
	
	@Autowired
	private CustomerCrudRepository customerReop;
	
	
	/**
	 * list페이지에서 댓글수 함께 출력하기
	 * */
	@RequestMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo , Model model ) {
		Pageable page=vo.makePageable(0, "bno");
		
		//Page<WebBoard> result = repo.findAll(repo.makePredicate(null, null), page);
		
		//검색 추가후 
		Page<Object [] > result = customerReop.getCustomerPage(vo.getType(), vo.getKeyword(), page);
		
		
		log.info(""+page);
		log.info("" + result);
		
		log.info("Total Page Number : " + result.getTotalPages());
		
		model.addAttribute("result", new PageMaker<>(result));
	}
	
	//////////////////////////////////////////////////////////////////
/*	@RequestMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo , Model model ) {
		Pageable page=vo.makePageable(0, "bno");
		
		//Page<WebBoard> result = repo.findAll(repo.makePredicate(null, null), page);
		
		//검색 추가후 
		Page<WebBoard> result = repo.findAll(repo.makePredicate(vo.getType(), vo.getKeyword()), page);
		
		
		log.info(""+page);
		log.info("" + result);
		
		log.info("Total Page Number : " + result.getTotalPages());
		
		model.addAttribute("result", new PageMaker<>(result));
	}*/
 
	/*@RequestMapping("/list")
	public void list(@PageableDefault(
			direction=Sort.Direction.DESC, 
			sort="bno" , 
			size=10 , 
			page = 0) Pageable page , Model model ) {
		
		log.info("list() called...." + page);
	}*/
	
	
	/**
	 * 입력 화면
	 * */
	@GetMapping("register")
	public void registerGet(@ModelAttribute("vo") WebBoard vo) {
		log.info("register   Get" + "");
	}
	
	/**
	 * 게시물 등록
	 * 
	 *  : spring 3.1번젼 부터 RedirectAttributes 추가   - https://blog.naver.com/platinasnow/30167012215 참고 
	 *      RedirectAttributes는 redirect방식으로 이동하고 싶으면서 값을 함께 전달해야할때 주렁주렁 달면 복잡하다.
	 *      이럴때 임시적으로 저장하는 것 - FlashMap 이라는 잠깐 동안 담을 수 있는 맵에 값을 담았다가 값을 보내준 후에는 증발해버리는 기능을 가진 맵
	 *      일단 이것을 사용하기 위해서는 스프링 서블릿 파일에 <mvc:annotation-driven />를 먼저 추가
	 * */
	@PostMapping("register")
	public String registerPost(@ModelAttribute("vo") WebBoard vo , RedirectAttributes rttr) {
		log.info("register   Post" + "");
		log.info(vo + "");
		
		repo.save(vo);//DB에 저장
		
		rttr.addFlashAttribute("msg","suuccess");
		rttr.addFlashAttribute("message","정상적으로 등록되었습니다.");
		 return "redirect:/boards/list";
		
	}
	
	
	/**
	 * 상세보기 
	 * */
	@GetMapping("/view")
	public void view(Long bno , @ModelAttribute("pageVO") PageVO vo , Model model) {
		log.info("BNO : " + bno);
		
		repo.findById(bno).ifPresent(board-> model.addAttribute("vo", board));
	}
	
	/**
	 * 게시물 수정 / 삭제 클릭
	 * */
	@GetMapping("/modify")
	public void modify(Long bno , @ModelAttribute("pageVO") PageVO vo , Model model) {
		log.info("Modify BNO : " + bno);
		repo.findById(bno).ifPresent(board-> model.addAttribute("vo", board));
	}
	/**
	 * 게시물삭제
	 * */
     @PostMapping("/delete")
     public String delete(Long bno , PageVO vo , RedirectAttributes rttr) {
    	 log.info("Delete Bno" + bno);
    	 
    	 repo.deleteById(bno);
    	 
    	 rttr.addFlashAttribute("msg","suuccess");
    	 rttr.addFlashAttribute("message", "삭제되었습니다.");
    	 
    	 rttr.addAttribute("page", vo.getPage()); //addFlashAttribute와 다르게 url에 보인다.
    	 rttr.addAttribute("size", vo.getSize());
    	 rttr.addAttribute("type", vo.getType());
    	 rttr.addAttribute("keyword", vo.getKeyword());
    	 
    	 
    	 /*rttr.addFlashAttribute("page", vo.getPage()); //addFlashAttribute와 다르게 url에 보인다.
    	 rttr.addFlashAttribute("size", vo.getSize());
    	 rttr.addFlashAttribute("type", vo.getType());
    	 rttr.addFlashAttribute("keyword", vo.getKeyword());*/
    	 
    	 return "redirect:/boards/list";
     }
     
     //수정하기
     @PostMapping("/modify")
     public String modifyPost(WebBoard board, PageVO vo, RedirectAttributes rttr) {
    	 log.info("Modify WebBoard : " + board);
    	 
    	 repo.findById(board.getBno()).ifPresent(origin->{
    		 origin.setTitle(board.getTitle());
    		 origin.setContent(board.getContent());
    		 
    		 repo.save(origin);
    		 
    		 rttr.addFlashAttribute("msg" ,"success");
    		 rttr.addFlashAttribute("message" ,"수정완료되었습니다.");
    		 
    		 rttr.addAttribute("bno",origin.getBno());
    	 });
    	 
    	 //페이징과 검색했던 결과로 이동하는 경우
    	 rttr.addAttribute("page", vo.getPage()); //addFlashAttribute와 다르게 url에 보인다.
    	 rttr.addAttribute("size", vo.getSize());
    	 rttr.addAttribute("type", vo.getType());
    	 rttr.addAttribute("keyword", vo.getKeyword());
    	 
    	 return "redirect:/boards/view";
     }
     
     
     @GetMapping("/hee")
     public String test() {
    	 return "잼 있다~~";
     }

}




























