package itemStudent;

public class Student {
    private int id;
    private String name;
    private int age;
    private String homeaddress;

    public Student() {
    }

    public Student(int id, String name, int age, String homeaddress) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.homeaddress = homeaddress;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取
     * @return homeaddress
     */
    public String getHomeaddress() {
        return homeaddress;
    }

    /**
     * 设置
     * @param homeaddress
     */
    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

}
