
import java.security.Signature;
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
public class PostService {
    static Scanner sc = new Scanner(System.in);
    static List<Author> authors = new ArrayList<>();
    static List<Post> posts = new ArrayList<>();
    public static void main(String[] args) {

        while (true) {
            System.out.println("원하시는 서비스 번호를 입력해주세요 :\n 1. 회원가입\n 2. 회원 전체 목록 조회\n 3. 회원 상세 조회\n 4. 게시글 작성\n 5. 게시글 목록 조회\n 6. 게시글 상세 조회");
            int input = sc.nextInt();
            if (input == 1) {           // 회원가입
                SignAuthor();
            } else if (input == 2) {    // 전체회원목록 조회
                AllAuhtorInquiry();
            } else if (input == 3) {    // 회원 상세 조회
                AuthorDetail();
            } else if (input == 4) {
                WritePost();            // 게시글 작성

            } else if (input == 5) {
                AllPostInquiry();       // 전체게시글 조회
            } else if (input == 6) {
                PostDetail();              // 게시글 상세 조회

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
    }
    public static void AllAuhtorInquiry(){
        System.out.println("전체 회원목록을 조회합니다");
        for (int i = 0; i < authors.size(); i++) {
            System.out.print("회원번호와 이메일 ");
            authors.get(i).authorInquiry();
        }
    }
    public static void AuthorDetail(){
        System.out.println("상세 조회 페이지입니다. 조회할 회원의 id를 입력해주세요");
        Long id = sc.nextLong();
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getA_id().equals(id)) {
                authors.get(i).detailAuthor(id);
                break;
            }
        }
    }
    public static void WritePost(){
        System.out.println("게시글 작성을 위해 이메일 인증을 진행합니다.");
        String em = sc.next();

        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getEmail().equals(em)) {
                System.out.println("인증에 성공했습니다.");
                System.out.println("작성할 게시글의 제목을 입력해주세요");
                String ti = sc.next();
                System.out.println("작성할 게시글의 내용을 입력해주세요");
                String con = sc.next();
                // 여기서 이메일을 Author 객체로 받아야....
                Post post = new Post(ti, con, authors.get(i));
                posts.add(post);
                System.out.println("작성완료");
            } else {
                System.out.println("인증에 실패했습니다. 처음 화면으로 돌아갑니다");
                continue;
            }
        }
    }
    public static void AllPostInquiry(){
        System.out.println("전체 게시글 목록을 조회합니다.");
        for (int i = 0; i < posts.size(); i++) {
            posts.get(i).postInquiry();
        }
    }
    public static void PostDetail(){
        System.out.println("상세 조회 페이지입니다. 조회할 게시글의 id를 입력해주세요");
        System.out.println("게시글의 id, 제목, 저자의 정보(이름 || 이메일 || id)가 출력됩니다.");
        Long id = sc.nextLong();
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getP_id().equals(id)) {
                posts.get(i).detailPost(id);
            }
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

    public void authorInquiry() {      // 전체 조회
        for (int i = 0; i < author_id; i++) {
            System.out.println("id는 " + a_id + "입니다. " + "이메일은 " + email + "입니다.");
        }
    }

    public void detailAuthor(long id) {
        System.out.println("id : " + a_id + ", 이름 : " + name + " , email : " + email + ", 비밀번호 : " + password);
    }


}

class Post {
    static Long post_id = 0L;
    private Long p_id;
    private String title;
    private String contents;
    private Author author;


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

    public void postInquiry() {
        for (int i = 0; i < post_id; i++) {
            System.out.println("id는 " + p_id + "입니다. " + "제목은 " + title + "입니다.");
        }
    }

    public void detailPost(long id) {
        System.out.println("id : " + p_id + ", 제목 : " + title + ", 내용 : " + contents + " , 저자의 email : " + author.getEmail());
    }
}

