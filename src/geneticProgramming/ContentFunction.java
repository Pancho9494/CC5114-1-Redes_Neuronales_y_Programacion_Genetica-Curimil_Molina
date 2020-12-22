package geneticProgramming;

public abstract class ContentFunction extends Content {
    private char content;

    public ContentFunction(char content){
        this.content = content;
    }

    @Override
    public void setContent(Object content) {
        this.content = (char) content;
    }

    @Override
    public Object getContent() {
        return content;
    }

    public abstract double operate(Content op1, Content op2);
}
