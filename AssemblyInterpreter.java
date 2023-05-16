import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AssemblyInterpreter {
    private static final Map<String, Integer> registers = new HashMap<>();

    public static void main(String[] args) {
        // Initialize registers
        registers.put("REG1", 0);
        registers.put("REG2", 0);

        // Read the assembly program
        String[] program = {
                "; Example Assembly Program",
                "MV REG1, #88000",
                "MV REG2, #12000",
                "ADD REG1, REG2",
                "ADD REG1, 600",
                "SHOW REG1"
        };

        // Execute the assembly program and store output in a file
        StringBuilder output = new StringBuilder();
        for (String instruction : program) {
            if (instruction.startsWith("MV")) {
                mv(instruction);
            } else if (instruction.startsWith("ADD")) {
                add(instruction);
            } else if (instruction.startsWith("SHOW")) {
                output.append(show(instruction)).append("\n");
            }
        }

        // Store the output in a file
        String filePath = "output.txt";
        storeOutputInFile(output.toString(), filePath);
    }

    private static void mv(String instruction) {
        String[] parts = instruction.split(",");
        String register = parts[0].split(" ")[1].trim();
        int value = Integer.parseInt(parts[1].split("#")[1].trim());
        registers.put(register, value);
    }

    private static void add(String instruction) {
        String[] parts = instruction.split(",");
        String register = parts[0].split(" ")[1].trim();
        String operand = parts[1].trim();
        int registerValue = registers.getOrDefault(register, 0);
        int value = operand.startsWith("#") ? Integer.parseInt(operand.substring(1))
                : registers.getOrDefault(operand, 0);
        registers.put(register, registerValue + value);
    }

    private static String show(String instruction) {
        String register = instruction.split(" ")[1].trim();
        return "Value in " + register + ": " + registers.get(register);
    }

    private static void storeOutputInFile(String output, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(output);
            System.out.println("Output successfully stored in the file: " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while storing the output: " + e.getMessage());
        }
    }
}
