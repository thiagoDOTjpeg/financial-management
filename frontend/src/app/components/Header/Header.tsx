import Image from "next/image";
import arrow from "../../assets/arrow.svg";
import * as S from "./headerStyle";

export default function Header() {
  return (
    <S.WrapperHeader>
      <S.Mes>
        <span>Novembro</span>
        <Image src={arrow} alt="arrow" />
      </S.Mes>
      <S.Entrada>
        <span>Entradas</span>
        <p>R$: XXXX.XX</p>
      </S.Entrada>
      <S.Saida>
        <span>Saídas</span>
        <p>R$: XXXX.XX</p>
      </S.Saida>
      <S.SaldoRestante>
        <span>Saldo Restante</span>
        <p>R$: XXXX.XX</p>
      </S.SaldoRestante>
    </S.WrapperHeader>
  );
}
