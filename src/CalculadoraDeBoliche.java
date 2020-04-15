import java.util.Scanner;

public class CalculadoraDeBoliche {
    private static Scanner teclado = new Scanner(System.in);
    private static int[][] frames = new int[11][3];
    private static int frameDeBonus = frames.length - 1;

    public static void main(String[] args) {
        int decimoFrameDoJogo = frameDeBonus - 1;
        int primeiraJogadaDoFrameCorrente = 0;
        int pontuacaoTotalDoFrameCorrente = 1;
        int segundaJogadaDoFrameCorrente = 2;
        int numeroDePinosDerrubadosNaPrimeiraJogada = 0;
        int numeroDePinosDerrubadosNaSegundaJogada = 0;
        String frameSendoJogadoAtualmente;
        System.out.println("calculadora de boliche\nDigite os pontos dos frames e o resultado será impresso na tela assim que a partida terminar.");
        for (int frameCorrente = 0; frameCorrente < frames.length; frameCorrente++) {
            frameSendoJogadoAtualmente = frameCorrente == frameDeBonus ? "Bônus" : String.valueOf(frameCorrente + 1);
            System.out.println("Frame atual: " + frameSendoJogadoAtualmente + "\nDigite o número de pinos que foram derrubados na primeira jogada: ");
            numeroDePinosDerrubadosNaPrimeiraJogada = teclado.nextInt();
            frames[frameCorrente][primeiraJogadaDoFrameCorrente] = numeroDePinosDerrubadosNaPrimeiraJogada;
            if (ehStrike(frameCorrente)) {
                System.out.println("Strike!");
                if (frameCorrente != frameDeBonus) {
                    continue;
                }
            }
            if (frameCorrente != frameDeBonus || ehStrike(decimoFrameDoJogo)) {
                System.out.println("Digite o número de pinos que foram derrubados na segunda jogada: ");
                numeroDePinosDerrubadosNaSegundaJogada = teclado.nextInt();
                frames[frameCorrente][segundaJogadaDoFrameCorrente] = numeroDePinosDerrubadosNaSegundaJogada;
            }
                if (frameCorrente == decimoFrameDoJogo && !ehSpare(frameCorrente)) {
                    break;
                }
                if (ehSpare(frameCorrente)) {
                    System.out.println("Spare!");
                }
            }
            System.out.println("A partida terminou, agora vamos ver o placar.");
            calcularEExibirPontuacoes();
        }

        private static void calcularEExibirPontuacoes () {
            int placarGeral = 0;
            int jogadaACalcularParaStrike = 0;
            int primeiroFrameDepoisDoStrikeSendoCalculadoNoMomento = 0;
            int segundoFrameDepoisDoStrikeSendoCalculadoNoMomento = 0;
            int primeiraJogadaDoFrame = 0;
            int segundaJogadaDoFrame = 2;
            int pontuacaoDoFrame = 1;
            for (int frameSendoCalculadoNoMomento = 0; frameSendoCalculadoNoMomento < frameDeBonus; frameSendoCalculadoNoMomento++) {
                frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] = placarGeral;
                if (ehStrike(frameSendoCalculadoNoMomento)) {
                    placarGeral = calcularStrike(frameSendoCalculadoNoMomento);
                } else if (ehSpare(frameSendoCalculadoNoMomento)) {
                    placarGeral = calcularSpare(frameSendoCalculadoNoMomento);
                } else {
                    placarGeral = calcularPontuacaoNormal(frameSendoCalculadoNoMomento);
                }
                System.out.println("Frame " + (frameSendoCalculadoNoMomento + 1) + ": " + placarGeral);
            }
        }

        private static boolean ehStrike ( int frame){
            int primeiraJogada = 0;
            return frames[frame][primeiraJogada] == 10;
        }

        private static boolean ehSpare ( int frameCorrente){
            int primeiraJogadaDoFrameCorrente = 0;
            int segundaJogadaDoFrameCorrente = 2;
            return frames[frameCorrente][segundaJogadaDoFrameCorrente] > 0 && (frames[frameCorrente][primeiraJogadaDoFrameCorrente] + frames[frameCorrente][segundaJogadaDoFrameCorrente]) == 10;
        }

        private static int calcularStrike ( int frameSendoCalculadoNoMomento){
            int primeiroFrameDepoisDoStrikeSendoCalculadoNoMomento = frameSendoCalculadoNoMomento + 1;
            int segundoFrameDepoisDoStrikeSendoCalculadoNoMomento = frameSendoCalculadoNoMomento + 2;
            int primeiraJogadaDoFrame = 0;
            int segundaJogadaDoFrame = 2;
            int pontuacaoDoFrame = 1;
            int jogadaACalcularParaStrike = 0;

            frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] += 10;
            jogadaACalcularParaStrike = frames[primeiroFrameDepoisDoStrikeSendoCalculadoNoMomento][primeiraJogadaDoFrame];
            frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] += jogadaACalcularParaStrike;
            jogadaACalcularParaStrike = (frames[primeiroFrameDepoisDoStrikeSendoCalculadoNoMomento][primeiraJogadaDoFrame] == 10 && primeiroFrameDepoisDoStrikeSendoCalculadoNoMomento != frameDeBonus) ? frames[segundoFrameDepoisDoStrikeSendoCalculadoNoMomento][primeiraJogadaDoFrame] : frames[primeiroFrameDepoisDoStrikeSendoCalculadoNoMomento][segundaJogadaDoFrame];
            frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] += jogadaACalcularParaStrike;
            return frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame];
        }

        private static int calcularSpare ( int frameSendoCalculadoNoMomento){
            int primeiroFrameDepoisDoSpareSendoCalculadoNoMomento = frameSendoCalculadoNoMomento + 1;
            int primeiraJogadaDoFrame = 0;
            int pontuacaoDoFrame = 1;
            int segundaJogadaDoFrame = 2;

            int jogadaACalcularParaSpare = frames[primeiroFrameDepoisDoSpareSendoCalculadoNoMomento][primeiraJogadaDoFrame];
            frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] += 10;
            frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] += jogadaACalcularParaSpare;
            return frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame];
        }

        private static int calcularPontuacaoNormal ( int frameSendoCalculadoNoMomento){
            int primeiraJogadaDoFrame = 0;
            int pontuacaoDoFrame = 1;
            int segundaJogadaDoFrame = 2;

            frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame] += (frames[frameSendoCalculadoNoMomento][primeiraJogadaDoFrame] + frames[frameSendoCalculadoNoMomento][segundaJogadaDoFrame]);
            return frames[frameSendoCalculadoNoMomento][pontuacaoDoFrame];
        }
    }