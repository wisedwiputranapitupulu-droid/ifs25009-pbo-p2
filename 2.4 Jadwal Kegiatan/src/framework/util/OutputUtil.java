package framework.util;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.PrintStream;

/**
 * Utility untuk menahan (buffer) output layar saat program dijalankan tanpa terminal
 * interaktif (stdin/stdout di-pipe, misalnya oleh test runner).
 *
 * Output baru dikeluarkan saat program selesai normal ({@link #flush()}).
 * Jika program berhenti karena exception (mis. input habis sebelum "x"),
 * output yang tertahan dibuang sehingga hanya pesan error yang muncul.
 * Pada terminal interaktif, output tetap tampil langsung seperti biasa.
 */
public final class OutputUtil {
    private static final PrintStream realOut = System.out;
    private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    private OutputUtil() {
    }

    /** Mengalihkan System.out ke buffer jika program tidak berjalan di terminal interaktif. */
    public static void install() {
        if (!isInteractive()) {
            System.setOut(new PrintStream(buffer, false));
        }
    }

    /** Mengeluarkan seluruh output yang tertahan ke stdout asli. Dipanggil saat program selesai normal. */
    public static void flush() {
        System.out.flush();
        if (buffer.size() > 0) {
            realOut.write(buffer.toByteArray(), 0, buffer.size());
            buffer.reset();
        }
        realOut.flush();
    }

    /** True jika stdin dan stdout terhubung ke terminal (Console.isTerminal() tersedia sejak JDK 22). */
    private static boolean isInteractive() {
        Console console = System.console();
        if (console == null) {
            return false;
        }
        try {
            return (boolean) Console.class.getMethod("isTerminal").invoke(console);
        } catch (ReflectiveOperationException e) {
            return true;
        }
    }
}
