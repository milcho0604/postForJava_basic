import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//게시판 회원가입 서비스
//1 .회원가입
//-> author 클래스 : id, name, email, password
//2. 회원 전체 목록 조회
//-> id, email 조회
//3. 회원 상세 조회(by id로 조회)
//-> id, name, email, password
//4. 게시글 작성(post클래스)
//->id, title, contents, email로 본인 인증(입력받되, Author 객체)
//5. 게시글 목록 조회
//-> id,title
//6. 게시글 상세 조회
//-> id, title, contents, 저자(email or name or author_id)
//7. 댓글 달기
public class PostService {
    static Scanner sc = new Scanner(System.in);
    static List<Author> authors = new ArrayList<>();
    static List<Post> posts = new ArrayList<>();
    public static void main(String[] args) {

        while (true) {
            System.out.println("원하시는 서비스 번호를 입력해주세요 :\n 1. 회원가입\n 2. 회원 전체 목록 조회\n 3. 회원 상세 조회\n " +
                    "4. 게시글 작성\n 5. 게시글 목록 조회\n 6. 게시글 상세 조회\n 7. 댓글 달기\n 8. 내가 단 댓글 조회");

            int input = sc.nextInt();
            sc.nextLine();
            switch (input) {
                case 0:
                    System.out.println("프로그램을 종료합니다");
                    return;
                case 1:
                    SignAuthor();           // 회원가입
                    break;
                case 2:
                    AllAuthorInquiry();     // 전체회원목록 조회
                    break;
                case 3:
                    AuthorDetail();         // 회원 상세 조회
                    break;
                case 4:
                    WritePost();            // 게시글 작성
                    break;
                case 5:
                    AllPostInquiry();       // 전체게시글 조회;
                    break;
                case 6:
                    PostDetail();           // 게시글 상세 조회
                    break;
                case 7:
                    AddComment();           // 댓글 달기
                    break;
                case 8:
                    CommentInquiry();       // 내가 단 댓글 조회
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }

    public static void SignAuthor(){
        System.out.println("회원가입을 위해 이름, 이메일, 비밀번호를 작성해주세요");
        String name = sc.nextLine();
        String email = sc.nextLine();
        String password = sc.nextLine();
        Author author = new Author(name, email, password);
        authors.add(author);
        System.out.println("회원가입이 완료되었습니다.");
    }

    public static void AllAuthorInquiry(){
        System.out.println("전체 회원목록을 조회합니다");
        for (Author author : authors) {
            author.authorInquiry();
        }
    }

    public static void AuthorDetail(){
        System.out.println("상세 조회 페이지입니다. 조회할 회원의 id를 입력해주세요");
        Long id = sc.nextLong();
        sc.nextLine(); // Consume newline left-over
        for (Author author : authors) {
            if (author.getA_id().equals(id)) {
                author.detailAuthor(id);
                break;
            }
        }
    }

    public static void WritePost(){
        System.out.println("게시글 작성을 위해 이메일 인증을 진행합니다.");
        String em = sc.nextLine();
        boolean authenticated = false;

        for (Author author : authors) {
            if (author.getEmail().equals(em)) {
                authenticated = true;
                System.out.println("인증에 성공했습니다.");
                System.out.println("작성할 게시글의 제목을 입력해주세요");
                String ti = sc.nextLine();
                System.out.println("작성할 게시글의 내용을 입력해주세요");
                String con = sc.nextLine();
                Post post = new Post(ti, con, author);
                posts.add(post);
                System.out.println("작성완료");
                break;
            }
        }

        if (!authenticated) {
            System.out.println("인증에 실패했습니다. 처음 화면으로 돌아갑니다");
        }
    }

    public static void AllPostInquiry(){
        System.out.println("전체 게시글 목록을 조회합니다.");
        for (Post post : posts) {
            post.postInquiry();
        }
    }

    public static void PostDetail(){
        System.out.println("상세 조회 페이지입니다. 조회할 게시글의 id를 입력해주세요");
        System.out.println("게시글의 id, 제목, 저자의 정보(이름 || 이메일 || id)가 출력됩니다.");
        Long id = sc.nextLong();
        sc.nextLine(); // Consume newline left-over
        for (Post post : posts) {
            if (post.getP_id().equals(id)) {
                post.detailPost(id);
            }
        }
    }

    public static void AddComment(){
        System.out.println("댓글을 달 게시글의 id를 입력해주세요");
        Long postId = sc.nextLong();
        sc.nextLine();
        Post post = null;

        for (Post p : posts) {
            if (p.getP_id().equals(postId)) {
                post = p;
                break;
            }
        }

        if (post == null) {
            System.out.println("해당 id의 게시글이 존재하지 않습니다. 초기 화면으로 돌아갑니다.");
            return;
        }

        int attempts = 0;
        boolean authenticated = false;
        while (attempts < 3 && !authenticated) {
            System.out.println("댓글 작성을 위해 이메일 인증을 진행합니다.");
            String email = sc.nextLine();

            for (Author author : authors) {
                if (author.getEmail().equals(email)) {
                    authenticated = true;
                    System.out.println("인증에 성공했습니다.");
                    System.out.println("작성할 댓글의 내용을 입력해주세요");
                    String content = sc.nextLine();
                    Comment comment = new Comment(content, author, post);
                    post.addComment(comment);
                    System.out.println("댓글 작성 완료");
                    break;
                }
            }

            if (!authenticated) {
                attempts++;
                if (attempts < 3) {
                    System.out.println("인증에 실패했습니다. 다시 시도해주세요.");
                } else {
                    System.out.println("인증에 3번 실패했습니다. 처음 화면으로 돌아갑니다.");
                }
            }
        }
    }

    public static void CommentInquiry(){
        System.out.println("본인이 작성한 댓글을 조회하기 위해 이메일과 비밀번호 인증을 진행합니다.");

        int attempts = 0;
        boolean authenticated = false;
        Author currentAuthor = null;

        while (attempts < 3 && !authenticated){
            System.out.println("이메일을 입력해주세요");
            String email = sc.next();
            System.out.println("비밀번호를 입력해주세요");
            String password = sc.next();
            for(Author author : authors){
                if(author.getEmail().equals(email) && author.getPassword().equals(password)){
                    authenticated = true;
                    currentAuthor = author;
                    break;
                }
            }

            if (!authenticated) {
                attempts++;
                if (attempts < 3) {
                    System.out.println("인증에 실패했습니다. 다시 시도해주세요.");
                } else {
                    System.out.println("인증에 3번 실패했습니다. 처음 화면으로 돌아갑니다.");
                    return;
                }
            }
        }

        if (authenticated && currentAuthor != null) {
            currentAuthor.printComments(posts);
        }
    }
}

class Author {

    static Long author_id = 0L;
    private Long a_id;
    private String name;
    private String email;
    private String password;

    public Author(String email) {
        this.email = email;
    }

    Author(String name, String email, String password) {
        author_id += 1;
        a_id = author_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static Long getAuthor_id() {
        return author_id;
    }

    public Long getA_id() {
        return a_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void authorInquiry() {
        System.out.println("id는 " + a_id + "입니다. " + "이메일은 " + email + "입니다.");
    }

    public void detailAuthor(long id) {
        String maskedPassword = maskPassword(password);
        System.out.println("id : " + a_id + ", 이름 : " + name + " , email : " + email + ", 비밀번호 : " + maskedPassword);
    }

    public void printComments(List<Post> posts) {
        System.out.println("본인이 작성한 댓글 목록입니다.");
        for (Post post : posts) {
            for (Comment comment : post.getComments()) {
                if (comment.getAuthor().equals(this)) {
                    System.out.println("게시글 제목: " + post.getTitle() + ", 게시글 ID: " + post.getP_id() + ", 댓글 작성자: " + this.getEmail() + ", 댓글 내용: " + comment.getContent());
                }
            }
        }
    }

    // 비밀번호 마스킹 처리를 위한 메서드 구현
    private String maskPassword(String password){
        int length = password.length();
        if (length <= 4) {
            return "****"; // 비밀번호가 4자리 이하인 경우 전체를 마스킹
        } else {
            String visiblePart = password.substring(0, 4); // 앞 4자리는 보이도록 처리
            String maskedPart = password.substring(4).replaceAll(".", "*"); // 나머지는 마스킹 처리
            return visiblePart + maskedPart;
        }
    }
}

class Post {
    static Long post_id = 0L;
    private Long p_id;
    private String title;
    private String contents;
    private Author author;
    private List<Comment> comments = new ArrayList<>();

    Post(String title, String contents, Author author) {
        post_id += 1;
        p_id = post_id;
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public static Long getPost_id() {
        return post_id;
    }

    public Long getP_id() {
        return p_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void postInquiry() {
        System.out.println("id는 " + p_id + "입니다. " + "제목은 " + title + "입니다.");
    }

    public void detailPost(long id) {
        System.out.println("id : " + p_id + ", 제목 : " + title + ", 내용 : " + contents + " , 저자의 email : " + author.getEmail());
        for (Comment comment : comments) {
            comment.commentDetail();
        }
    }
}

class Comment {
    private String content;
    private Author author;
    private Post post;

    Comment(String content, Author author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public Author getAuthor() {
        return author;
    }

    public Post getPost() {
        return post;
    }

    public void commentDetail() {
        String maskedEmail = maskEmail(author.getEmail());
        System.out.println("댓글 내용: " + content + ", 작성자: " + maskedEmail + ", 게시글 제목: " + post.getTitle() + ", 게시글 ID: " + post.getP_id());
    }

    // 이메일 마스킹 처리를 위한 메서드 구현
    private String maskEmail(String email){
        int length = email.length();
        if (length <= 4) {
            return "****"; // 이메일의 길이가 4자리 이하인 경우 전체를 마스킹
        } else {
            String visiblePart = email.substring(0, 4); // 앞 4자리는 보이도록 처리
            String maskedPart = email.substring(4).replaceAll(".", "*"); // 나머지는 마스킹 처리
            return visiblePart + maskedPart;
        }
    }
}
