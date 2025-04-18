import styled from "styled-components";

export const WrapperHeader = styled.header`
  display: flex;
  flex-direction: column;
  align-items: center;

  gap: 20px;

  span {
  color: #062a58;
  font-size: 24px;
  font-family: Poppins;
  font-weight: bold;
  opacity: 50%;
}
`;

export const WrapperValores = styled.div`
  display: flex;

  gap: 90px;
`;

export const Entrada = styled.div`
  display: grid;
  grid-template-columns: 400px;
  grid-template-rows: 39px 361px;

  width: 400px;
  height: 100px;
  background-color: #d6dac8;
  border-radius: 25px;

  box-shadow: 0 4px 4px 0 rgba(0,0,0,0.25);

  span {
    margin-left: 13px;
    margin-top: 5px;
    grid-row: 1;
  }

  p {
    font-family: Poppins;
    font-size: 35px;
    color: #062a58;
    opacity: 75%;

    justify-self: center;
    grid-row: 2;
  }
`;

export const Saida = styled.div`
  display: grid;
  grid-template-columns: 400px;
  grid-template-rows: 39px 361px;

  width: 400px;
  height: 100px;
  background-color: #d6dac8;
  border-radius: 25px;

  box-shadow: 0 4px 4px 0 rgba(0,0,0,0.25);;

  span {
    margin-left: 13px;
    margin-top: 5px;
    grid-row: 1;
  }

  p {
    font-family: Poppins;
    font-size: 35px;
    color: #062a58;
    opacity: 75%;
    justify-self: center;
    grid-row: 2;
  }
`;

export const Mes = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 178px;
  height: 33px;
  background-color: #d6dac8;
  border-radius: 25px;
  box-shadow: 0 4px 4px 0 rgba(0,0,0,0.25);

  justify-self: center;
  margin-top: 15px;

`;

export const SaldoRestante = styled.div`
  display: grid;
  grid-template-columns: 400px;
  grid-template-rows: 39px 361px;

  width: 400px;
  height: 100px;
  background-color: #d6dac8;
  border-radius: 25px;

  box-shadow: 0 4px 4px 0 rgba(0,0,0,0.25);

  span {
    margin-left: 13px;
    margin-top: 5px;
    grid-row: 1;
  }

  p {
    font-family: Poppins;
    font-size: 35px;
    color: #062a58;
    opacity: 75%;
    justify-self: center;
    grid-row: 2;
  }
`;