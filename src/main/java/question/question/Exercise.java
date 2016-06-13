package question.question;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Exercise {
	private @Getter @Setter String englishName;
	private @Getter @Setter String chineseName;
	private @Getter @Setter String article;
	private @Getter @Setter List<Question> questions;
	private @Getter @Setter String level;
	
	@Override
	public String toString() {
		return "Exercise [englishName=" + englishName + ", chineseName=" + chineseName + ", article=" + article
				+ ", questions=" + questions + ", level=" + level + "]";
	}
	
	
}

class Question {
	private @Getter @Setter String stem;
	private @Getter @Setter List<String> abilities;
	private @Getter @Setter List<String> options;
	private @Getter @Setter String answer;
	private @Getter @Setter int type;//1 主谓宾 2 翻译句子 3 主旨题
	private @Getter @Setter String text;
	private @Getter @Setter String option;
	
	@Override
	public String toString() {
		return "Question [stem=" + stem + ", abilities=" + abilities + ", options=" + options + ", answer=" + answer
				+ ", type=" + type + ", text=" + text + ", option=" + option +"]";
	}
	
	
	
	
}
