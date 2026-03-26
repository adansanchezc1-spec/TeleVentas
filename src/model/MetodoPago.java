
package model;

public class MetodoPago {

    public static final String TIPO           = "tarjeta_credito";
    private static final int   LONGITUD_TARJETA = 16;

    private String tipo;
    private String numeroTarjeta;

    public MetodoPago(String numeroTarjeta) {
        this.tipo          = TIPO;
        this.numeroTarjeta = numeroTarjeta;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getTipo()          { return tipo;          }
    public String getNumeroTarjeta() { return numeroTarjeta; }

    /**
     * Valida que el número de tarjeta tenga exactamente 16 dígitos numéricos.
     */
    public boolean verificarPago() {
        if (numeroTarjeta == null) return false;
        return numeroTarjeta.matches("\\d{" + LONGITUD_TARJETA + "}");
    }

    /**
     * Procesa el pago si la tarjeta es válida.
     * Retorna true si el pago fue exitoso, false si la tarjeta es inválida.
     */
    public boolean procesarPago(float monto) {
        if (!verificarPago()) {
            System.out.println("  [Pago] Tarjeta inválida. El pago no pudo procesarse.");
            return false;
        }
        System.out.printf("  [Pago] Pago de $%.2f procesado exitosamente con tarjeta **** **** **** %s.%n",
                monto, numeroTarjeta.substring(12));
        return true;
    }
}

