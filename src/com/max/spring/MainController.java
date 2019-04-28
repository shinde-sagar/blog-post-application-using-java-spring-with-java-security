package com.max.spring;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	private HttpSession session;
	private Users user;
	
	private boolean CheckUserisLogin()
	{
		this.user = (Users) this.session.getAttribute("user");
		if(this.user != null)
			return true;
		return false;
	}
	
	private boolean isUserAdmin()
	{
		if(this.user.getRole() == 1) {
			return true;
		}
		return false;
	}
	
	@RequestMapping(value="/changepassword",method=RequestMethod.POST)
	public ModelAndView ChangePasswordPost(HttpServletRequest request, HttpSession session) {
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		String username = request.getParameter("username");
		String old_password = request.getParameter("old_password");
		String new_password = request.getParameter("new_password");
		
		System.out.println("useername : " + username);
		
		ModelAndView mv = new ModelAndView("profile");
		mv.addObject("user",this.user);
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		UserList user_list = (UserList) context.getBean("userList");
		String result = user_list.ChangePassword(username, old_password, new_password);
		context.close();
		if(result != "true") {
			mv.addObject("error",result);
		}else {
			mv.addObject("success","successfully password changed");
		}
		return mv; 
	}
	
	@RequestMapping(value="/changepassword",method=RequestMethod.GET)
	public ModelAndView ChangePassword(HttpSession session) {
		
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		
		ModelAndView mv = new ModelAndView("profile");
		mv.addObject("user",this.user);
		return mv;
	}
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public ModelAndView UserLists(HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		if(!this.isUserAdmin()) {
			ModelAndView mv = new ModelAndView("error");
			mv.addObject("error","User don't have permission to access these page");
			return mv;
		}
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		UserList user_list = (UserList) context.getBean("userList");
		List<Users> users_list = user_list.getAllUser();
		context.close();
		ModelAndView mv = new ModelAndView("Userlist");
		mv.addObject("users_list",users_list);
		return mv;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request,HttpSession session)
	{
		//check login
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		List<Topics> getRecentTopics = topics.getRecentTopics();
		SectionsJDBC sections = (SectionsJDBC) context.getBean("SectionsJDBC");
		List<Sections> getAllSections = sections.getAllSections();
		context.close();
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("sections", getAllSections);
		mv.addObject("topics", getRecentTopics);
		mv.addObject("user",this.user);
		return mv;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/");
		}
		ModelAndView mv = new ModelAndView("loginForm");
		if(request.getParameter("message") != null) {
			mv.addObject("message",request.getParameter("message"));
		}
		return mv;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView loginPost(HttpServletRequest request,HttpSession session){
		
		this.session = session;
		if(this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/");
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		LoginUser User = (LoginUser) context.getBean("loginUser");
		String result = User.AttempLogin(username, password,session);
		context.close();
		System.out.println(result);
		if(result != "true")
		{
			ModelAndView mv = new ModelAndView("loginForm");
			mv.addObject("error", result);
			return mv;
		}
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public ModelAndView logout(HttpSession session){
		
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		try {
			Users user = (Users) session.getAttribute("user");
				System.out.println(user.getUsername());
				System.out.println(user.getPassword());
				System.out.println(user.getRole());
				session.removeAttribute("user");
			return new ModelAndView("redirect:/login");
		}
		catch (NullPointerException e) {
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public ModelAndView register(HttpSession session)
	{
		this.session = session;
		if(this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("registerForm");
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView registerPost(HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/");
		}
		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		RegisterUser User = (RegisterUser) context.getBean("registerUser");
		String result = User.CreateAccount(username, password);
		System.out.println(result);
		context.close();
		
		if(result != "true")
		{
			ModelAndView mv = new ModelAndView("registerForm");
			mv.addObject("error", result);
			return mv;
		}
		return new ModelAndView("redirect:/login?message=successfully user register");
	}
	
	@RequestMapping(value="/sections",method=RequestMethod.GET)
	public ModelAndView sections(HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		SectionsJDBC sections = (SectionsJDBC) context.getBean("SectionsJDBC");
		List<Sections> getAllSections = sections.getAllSections();
		context.close();
		ModelAndView mv = new ModelAndView("sections");
		mv.addObject("sections",getAllSections);
		return mv;
	}
	
	@RequestMapping(value="/sections",method=RequestMethod.POST)
	public ModelAndView sectionsPost(HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		String section_name = request.getParameter("section_name");
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		SectionsJDBC sections = (SectionsJDBC) context.getBean("SectionsJDBC");
		String result = sections.create(section_name,this.user.getId());
		List<Sections> getAllSections = sections.getAllSections();
		ModelAndView mv = new ModelAndView("sections");
		mv.addObject("sections",getAllSections);
		if(result == "true"){
			mv.addObject("recentAdd",section_name);
			mv.addObject("success","successfully section created");
		}
		else {
			mv.addObject("error",result);
		}
		context.close();
		
		
		return mv;
		
	}
	
	@RequestMapping(value="/sectionsDelete",method=RequestMethod.POST)
	public ModelAndView sectionsDelete(HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		int delete_id = Integer.parseInt(request.getParameter("delete_id"));
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		SectionsJDBC sections = (SectionsJDBC) context.getBean("SectionsJDBC");
		sections.delete(delete_id);
		context.close();
		return new ModelAndView("redirect:/sections");
	}
	@RequestMapping(value="/topics/{topic_id}",method=RequestMethod.GET)
	public ModelAndView topics(@PathVariable("topic_id") int id,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		List<Topics> getAllTopics = topics.getAllTopics(id);
		context.close();
		ModelAndView mv = new ModelAndView("topics");
		mv.addObject("topics",getAllTopics);
		mv.addObject("topics_id",id);
		return mv;
	}
	
	@RequestMapping(value="/topics/{topic_id}",method=RequestMethod.POST)
	public ModelAndView topicsPost(@PathVariable("topic_id") int id,HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
	
		String topic_name = request.getParameter("topic_name");
		System.out.println("id : " + id);
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		String result = topics.create(topic_name,id,this.user.getId());
		List<Topics> getAllTopics = topics.getAllTopics(id);
		ModelAndView mv = new ModelAndView("topics");
		mv.addObject("topics_id",id);
		mv.addObject("topics",getAllTopics);
		if(result == "true"){
			mv.addObject("recentAdd",topic_name);
			mv.addObject("success","successfully topic created");
		}
		else {
			mv.addObject("error",result);
		}
		context.close();
		return mv;
		
	}
	
	@RequestMapping(value="/topics/topicsDelete/{topic_id}",method=RequestMethod.GET)
	public ModelAndView topicsDelete2(@PathVariable("topic_id") int id,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("redirect:/topics/" + id);
	}
	
	@RequestMapping(value="/topics/topicsDelete/{topic_id}",method=RequestMethod.POST)
	public ModelAndView topicsDelete(@PathVariable("topic_id") int id,HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		
		int topic_id =  Integer.parseInt(request.getParameter("delete_id"));
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		boolean result = topics.delete(topic_id,id);
		List<Topics> getAllTopics = topics.getAllTopics(id);
		ModelAndView mv = new ModelAndView("topics");
		mv.addObject("topics",getAllTopics);
		if(result){
			mv.addObject("success","successfully topic deleted");
		}
		else {
			mv.addObject("error","failed to delete topic");
		}
		context.close();
		return mv;
		
	}
	
	@RequestMapping(value="/posts/{topic_id}",method=RequestMethod.GET)
	public ModelAndView posts(@PathVariable("topic_id") int id,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		PostsJDBC post =  (PostsJDBC) context.getBean("PostsJDBC");
		Topics topicData = topics.getTopics(id);
		List<Posts> postData = post.getAllPosts(id);
		context.close();
		ModelAndView mv = new ModelAndView("post");
		if(topicData != null) {
			mv.addObject("topic",topicData.getText());
			mv.addObject("id",id);
		}else {
			ModelAndView mv2 = new ModelAndView("error");
			mv.addObject("error","Invalid Url found");
			return mv2;
		}
		mv.addObject("posts",postData);
		return mv;
	}
	
	@RequestMapping(value="/posts/{topic_id}",method=RequestMethod.POST)
	public ModelAndView postsCreate(@PathVariable("topic_id") int id,HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		
		String post_txt =  request.getParameter("post_txt");
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		PostsJDBC post =  (PostsJDBC) context.getBean("PostsJDBC");
		String result = post.create(id, post_txt,this.user.getId());
		Topics topicData = topics.getTopics(id);
		List<Posts> postData = post.getAllPosts(id);
		context.close();
		ModelAndView mv = new ModelAndView("post");
		if(topicData != null) {
			mv.addObject("topic",topicData.getText());
			mv.addObject("posts",postData);
			mv.addObject("id",id);
		}else {
			ModelAndView mv2 = new ModelAndView("error");
			mv.addObject("error","Invalid Url found");
			return mv2;
		}
		if(result != "true"){
			mv.addObject("error",result);
			return mv;
		}
		mv.addObject("success","successfully post created");
		return mv;
	}
	
	@RequestMapping(value="/posts/postDelete/{id}",method=RequestMethod.GET)
	public ModelAndView postsDelete2(@PathVariable("id") int id,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("redirect:/posts/" + id);
	}
	
	@RequestMapping(value="/posts/postUpdate/{id}",method=RequestMethod.GET)
	public ModelAndView postsUpdate2(@PathVariable("id") int id,HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("redirect:/posts/" + id);
	}
	
	@RequestMapping(value="/posts/postUpdate/{id}",method=RequestMethod.POST)
	public ModelAndView postsUpdate(@PathVariable("id") int id,HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		int post_id = Integer.parseInt(request.getParameter("post_id"));
		String post_txt =  request.getParameter("post_update_txt");
		
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		PostsJDBC post =  (PostsJDBC) context.getBean("PostsJDBC");
		String result = post.update(post_id,id, post_txt);
		Topics topicData = topics.getTopics(id);
		List<Posts> postData = post.getAllPosts(id);
		context.close();
		ModelAndView mv = new ModelAndView("post");
		if(topicData != null) {
			mv.addObject("topic",topicData.getText());
			mv.addObject("posts",postData);
			mv.addObject("id",id);
		}else {
			ModelAndView mv2 = new ModelAndView("error");
			mv.addObject("error","Invalid Url found");
			return mv2;
		}
		if(result != "true"){
			mv.addObject("error",result);
			return mv;
		}
		mv.addObject("success","successfully post updated");
		return mv;
	}
	
	@RequestMapping(value="/posts/postDelete/{id}",method=RequestMethod.POST)
	public ModelAndView postsDelete(@PathVariable("id") int id,HttpServletRequest request,HttpSession session)
	{
		this.session = session;
		if(!this.CheckUserisLogin()) {
			return new ModelAndView("redirect:/login");
		}
		
		
		int post_id = Integer.parseInt(request.getParameter("delete_id"));
		ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) new ClassPathXmlApplicationContext("applicationContext.xml");
		TopicsJDBC topics = (TopicsJDBC) context.getBean("TopicsJDBC");
		PostsJDBC post =  (PostsJDBC) context.getBean("PostsJDBC");
		boolean result = post.delete(post_id);
		Topics topicData = topics.getTopics(id);
		List<Posts> postData = post.getAllPosts(id);
		context.close();
		ModelAndView mv = new ModelAndView("post");
		if(result) {
			mv.addObject("success","successfully post deleted");
		}else {
			mv.addObject("error","failed to delete post");
		}
		if(topicData != null) {
			mv.addObject("topic",topicData.getText());
			mv.addObject("id",id);
		}else {
			ModelAndView mv2 = new ModelAndView("error");
			mv.addObject("error","Invalid Url found");
			return mv2;
		}
		mv.addObject("posts",postData);
		return mv;
	}
	
	
	@RequestMapping(value="error")
	public ModelAndView error404(){
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("error","Invalid Url Found");
		return mv;
	}
}
