package question.question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadTxt {
	public static void main(String[] args) {
		StringBuilder builder = readTxt("C:\\Users\\ezjie118\\Desktop\\录题\\test.txt");
		createExercise(builder);
		String s = "But nature is indifferent to human notions of fairness, and a report by the Fish and Wildlife Service showed a worrisome drop in the populations of several species of North Atlantic turtles, notably loggerheads, which can grow to as much as 400 pounds. The South Florida nesting population, the largest, has declined by ";
		String s2 = "But nature is indifferent to human notions of fairness";
		System.out.println("aaa" + s.contains(s2));
	}
	
	private static StringBuilder readTxt(String filePath) {
		File file = new File(filePath);
		if(!file.exists()) {
			System.out.println(filePath + "is not exit!!!");
			return null;
		} else {
			BufferedReader br = null;
			StringBuilder builder = new StringBuilder();
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
				String line;
				while((line = br.readLine()) != null) {
					line = line.replace("\uFEFF", "");				
					if(line.startsWith("Lv")) {
						builder.append("////");
						builder.append(line);
						builder.append("\n");
						builder.append("///");
					} else if(line.matches("(\\d+\\.)(.*)")) {
						builder.append("///");
						builder.append(line);
						builder.append("\n");
					} else {
						builder.append(line);
						builder.append("\n");
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return builder;
		}
	}
	
	private static List<Exercise> createExercise(StringBuilder builder) {
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		String[] exercises = builder.toString().split("////");
		for(String exercise : exercises) {
			if((exercise == null) || (exercise.equals(""))) {
				continue;
			}
			String[] strings = exercise.split("///");
			Exercise e = new Exercise();
			List<Question> questions = new ArrayList<Question>();
			e.setChineseName(strings[0].replace("\\n", ""));
			e.setLevel(String.valueOf(strings[0].trim().charAt(2)));
			String chineseName = e.getChineseName().trim();
			String englishName = "Passage" + String.valueOf(chineseName.charAt(chineseName.length()-1));
			e.setEnglishName(englishName);
			e.setArticle(strings[1]);
			for(int i = 2; i < strings.length; i ++) {
				if(strings[i].trim().equals("")) {
					continue;
				}
				Question question = new Question();
				String[] quesInfos = strings[i].split("\\n"); 
				String[] infos = quesInfos[0].split("（");
				question.setStem(infos[0].split("\\.")[1]);
				String[] abilities = infos[1].replace("）", "").split("、");
				List<String> abilityList = new ArrayList<String>();
				for(String ability : abilities) {
					abilityList.add(ability);
				}
				question.setAbilities(abilityList);
				
				if(quesInfos[0].contains("主谓宾") && (e.getQuestions() == null)) {
					System.out.println(i);
					question.setType(1);
					List<String> options = new ArrayList<String>();
					for(int j = 0; j < 3; j++) {
						int length = quesInfos[2+j].split(":").length;
						String option = quesInfos[2+j].split(":")[length-1];
						options.add(option.toLowerCase());
					}
					String text = quesInfos[1].replace("|", "");
					String option = quesInfos[1].replace("|", "@");
					question.setText(text);
					question.setOption(option);
					question.setOptions(options);
				}else if((e.getQuestions() == null) && !(quesInfos[0].contains("主谓宾"))){
					String stem = "<u>"+question.getStem()+"</u>";
					System.out.println(e.getArticle().contains(question.getStem()));
					question.setAnswer(quesInfos[1]);
				} else {
					question.setType(3);
					List<String> options = new ArrayList<String>();
					Pattern pattern = Pattern.compile("([A-Z].)(.*)");
					for(int j = 1; j < 5; j++) {
						System.out.println(quesInfos[j]);
						Matcher matcher = pattern.matcher(quesInfos[j]);
						matcher.matches();
						String option = matcher.group(2);
						options.add(option);
					}
					String answer = quesInfos[5].split(":")[1];
					question.setAnswer(answer);
					question.setOptions(options);
					String text = e.getArticle();
					question.setText(text);
				}
				
				questions.add(question);
			}
			e.setQuestions(questions);
			System.out.println(e);
			exerciseList.add(e);
		}
		return exerciseList;
	}
}
